package it.memelabs.smartnebula.lmm.persistence.main.util;

import com.google.common.base.Stopwatch;
import it.memelabs.smartnebula.lmm.persistence.main.dto.Contract;
import org.slf4j.Logger;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Andrea Fossi.
 */
@Deprecated
public class ContractTreeUtils {
    private static final Logger LOG = getLogger(ContractTreeUtils.class);


    public static List<ContractNode> getTree(Collection<? extends Contract> contracts) {
        if (contracts.isEmpty()) return Collections.emptyList();
        else {
            Stopwatch stopwatch = Stopwatch.createStarted();
            // HashMap<Long, ContractNode> allContracts = new HashMap<Long, ContractNode>(contracts.size());
            // contracts.forEach(c -> allContracts.put(c.getId(), new ContractNode(c)));
            Map<Long, ContractNode> allContracts = contracts.stream().map(ContractNode::new).collect(Collectors.toMap(ContractNode::getId, Function.identity()));

            List<ContractNode> rootNodes = new ArrayList<>();
            allContracts.entrySet().forEach(e -> {
                ContractNode contractNode = e.getValue();
                if (contractNode.getReferenceContractId() == null || contractNode.getReferenceContractId().equals(contractNode.getId()))
                    rootNodes.add(contractNode);
                else
                    allContracts.get(contractNode.getReferenceContractId()).addChild(contractNode);
            });

            LOG.info("Time to build tree {} contracts: {}", contracts.size(), stopwatch.stop());
            return rootNodes;
        }
    }

    public static List<ContractNode> getFilteredTree(List<ContractNode> rootNodes, long companyId) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Set<Contract> allContract = new TreeSet<>((Contract o1, Contract o2) -> o1.getId().compareTo(o2.getId()));
        for (ContractNode cn : rootNodes) {
            findNode(cn, false, companyId, new Stack<>(), allContract);
        }
        LOG.info("Time to filter tree {} ", stopwatch.stop());
        return getTree(allContract);
    }

    /**
     * Recursive Algorithm
     *
     * @param node
     * @param companyId
     * @return
     */
    public static void findNode(ContractNode node, boolean found, long companyId, Stack<Contract> parentStack, Set<Contract> allContract) {

        if (!found &&
                (node.getContract().getCompanyId().equals(companyId) || node.getContract().getPerformingCompanyId().equals(companyId))) {
            found = true;
            allContract.addAll(parentStack);
        }
        if (found) allContract.add(node.getContract());

        parentStack.push(node.getContract());
        for (ContractNode n : node.getChildren()) {
            findNode(n, found, companyId, parentStack, allContract);
        }
        parentStack.pop();
        return;
    }

    /**
     * Depth first
     *
     * @param node
     * @param value
     * @return
     */
    public static boolean findNodeI(ContractNode node, int value) {
        Deque<ContractNode> stack = new ArrayDeque<ContractNode>();
        stack.push(node);
        while (!stack.isEmpty()) {
            ContractNode n = stack.pop();
            if (n.getContract().getCompanyId() == value)
                return true;
            for (ContractNode child : n.getChildren())
                stack.push(child);
        }
        return false;
    }

    /**
     * Recursive Algorithm
     *
     * @param node
     * @param value
     * @return
     */
    public static boolean findNodeR(ContractNode node, int value) {
        if (node.getContract().getCompanyId() == value)
            return true;
        for (ContractNode n : node.getChildren()) {
            if (findNodeR(n, value))
                return true;
        }
        return false;
    }

    public static class ContractNode {
        private final Contract contract;
        private final List<ContractNode> children = new ArrayList<>();

        public List<ContractNode> getChildren() {
            return children;
        }

        public ContractNode addChild(ContractNode child) {
            children.add(child);
            return this;
        }

        public ContractNode(Contract contract) {
            this.contract = contract;
        }

        public Long getId() {
            return contract.getId();
        }

        public Long getReferenceContractId() {
            return contract.getReferenceContractId();
        }

        public Contract getContract() {
            return contract;
        }
    }
}
