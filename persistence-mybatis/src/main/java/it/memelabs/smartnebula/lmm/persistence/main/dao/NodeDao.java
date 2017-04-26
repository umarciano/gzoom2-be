package it.memelabs.smartnebula.lmm.persistence.main.dao;

import it.memelabs.smartnebula.lmm.persistence.main.dto.Node;
import it.memelabs.smartnebula.lmm.persistence.main.dto.NodeExample;
import it.memelabs.smartnebula.lmm.persistence.main.mapper.NodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Andrea Fossi.
 */
@Service
public class NodeDao {
    private final NodeMapper nodeMapper;

    @Autowired
    public NodeDao(NodeMapper nodeMapper) {
        this.nodeMapper = nodeMapper;
    }


    public List<Node> selectAll() {
        NodeExample example = new NodeExample();
        example.setOrderByClause("id");
        return nodeMapper.selectByExample(example);
    }

    public Node findById(long id) {
        return nodeMapper.selectByPrimaryKey(id);
    }
}
