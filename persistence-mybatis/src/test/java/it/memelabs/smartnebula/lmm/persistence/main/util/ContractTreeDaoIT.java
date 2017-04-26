package it.memelabs.smartnebula.lmm.persistence.main.util;

import com.google.common.base.Stopwatch;
import it.mapsgroup.commons.collect.Tuple2;
import it.memelabs.smartnebula.lmm.persistence.AbstractDaoTestIT;
import it.memelabs.smartnebula.lmm.persistence.main.dao.ContractDao;
import it.memelabs.smartnebula.lmm.persistence.main.dao.ContractFilter;
import it.memelabs.smartnebula.lmm.persistence.main.dto.*;
import it.memelabs.smartnebula.lmm.persistence.main.enumeration.ContractCatalogType;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.CompanyMapper;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.ContractExMapper;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */
public class ContractTreeDaoIT extends AbstractDaoTestIT{
    private static final Logger LOG = getLogger(ContractTreeDaoIT.class);

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private ContractExMapper contractMapper;


    @Test
    public void testFindByFilter() throws Exception {
        UserLogin userLogin = getUserLogin();
        ContractFilter filter = new ContractFilter();
        contractDao.selectStates(getUserLogin());

        Stopwatch stopwatch = Stopwatch.createStarted();
        Tuple2<List<ContractEx>, Integer> ret = contractDao.findExByFilter(filter, userLogin);
        LOG.info("Time to load {} contracts: {}", ret.second(), stopwatch.stop());

        stopwatch = Stopwatch.createStarted();
        Map<Long, ContractWrapper> tree = new HashMap<>();
        List<ContractWrapper> roots = new ArrayList<ContractWrapper>();
        for (Contract n : ret.first()) {
            if (n.getReferenceContractId() == null)
                roots.add(new ContractWrapper(n));
            else {
                if (!tree.containsKey(n.getReferenceContractId()))
                    tree.put(n.getReferenceContractId(), new ContractWrapper(n));
                tree.get(n.getReferenceContractId()).children.add(n);
            }
        }
        LOG.info("Time to build tree: {}", stopwatch.stop());

        LOG.info("end");

    }

    private static class ContractWrapper {
        private Contract contract;
        public ArrayList<Contract> children = new ArrayList<>();

        public ContractWrapper(Contract contract) {
            this.contract = contract;
        }
    }


    @Test
    @Ignore
    public void contractGenerator() throws Exception {
        List<Company> companies = companyMapper.selectByExample(new CompanyExample());

        for (int i = 0; i < 40; i++) {
            int depth = 0;
            Contract c = getContract(companies.get(depth), companies.get(depth + 1), i + "", null);
            Long id = contractDao.create(c, emptyList(), getUserLogin());
            createChildren(companies, i + "", depth, id);
        }

    }

    private void createChildren(List<Company> companies, String key, int depth, Long parentId) {
        depth++;
        if (companies.size() > depth + 1) {
            for (int n = 0; n < 4; n++) {
                Contract c2 = getContract(companies.get(depth), companies.get(depth + 1), key + "." + n, parentId);
                Long id = contractDao.create(c2, emptyList(), getUserLogin());
                createChildren(companies, key + "." + n, depth, id);
            }
        }
    }

    private Contract getContract(Company a, Company b, String i, Long parentId) {
        Contract c = new Contract();
        c.setCompanyId(a.getId());
        c.setPerformingCompanyId(b.getId());
        c.setContractNumber("A-" + i);
        c.setReferenceContractId(parentId);
        c.setCustomerAuthorization(true);
        c.setContractTypeId(contractDao.selectCatalog(ContractCatalogType.TYPE, getUserLogin()).get(0).getId());
        c.setStateId(contractDao.selectStates(getUserLogin()).get(0).getId());
        return c;
    }

    private UserLogin getUserLogin() {
        Node node = new Node();
        node.setId(1L);
        UserLogin userLogin = new UserLogin();
        userLogin.setId(66L);
        userLogin.setActiveNode(node);
        return userLogin;
    }


    @Test
    public void testFindAll() throws Exception {
        UserLogin userLogin = getUserLogin();
        ContractFilter filter = new ContractFilter();
        contractDao.selectStates(getUserLogin());

        Tuple2<List<Contract>, Integer> ret = contractDao.findByFilter(filter, userLogin);
        List<Contract> first = ret.first();

        Stopwatch stopwatch = Stopwatch.createStarted();
        //todo return map
        //List<Map<String, Object>> test =contractMapper.selectByExampleWithRowboundsExForTree(new ContractExExample(),new RowBounds(0,Integer.MAX_VALUE));
        LOG.info("Time to load {} contracts: {}", first.size(), stopwatch.stop());
        // test.size();
        stopwatch = Stopwatch.createStarted();


        List<ContractTreeUtils.ContractNode> mainTree = ContractTreeUtils.getTree(first);

        List<ContractTreeUtils.ContractNode> filteredTree = ContractTreeUtils.getFilteredTree(mainTree, 1006);


        LOG.info("Time to build tree {} contracts: {}", first.size(), stopwatch.stop());
        LOG.info("TEST {}", filteredTree.size());


    }


}
