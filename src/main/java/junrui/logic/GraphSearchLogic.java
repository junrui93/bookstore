package junrui.logic;

import java.util.*;

import junrui.data.GraphResult;
import junrui.mapper.GraphEntityMapper;
import junrui.mapper.GraphRelationMapper;
import junrui.model.GraphEntity;
import junrui.model.GraphRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GraphSearchLogic {

    @Autowired
    private GraphEntityMapper entityMapper;

    @Autowired
    private GraphRelationMapper relationMapper;

    public GraphResult search(String value, int type) {
        Set<Integer> entityIds = null;
        if (type == 0) {
            entityIds = entityMapper.selectByClassAndValue("publication", "title", value);
        } else if (type == 1) {
            entityIds = entityMapper.selectByClassAndValue("author", "name", value);
        } else if (type == 2) {
            entityIds = entityMapper.selectByClassAndValue("venue", "name", value);
        }

        if (entityIds == null || entityIds.isEmpty()) {
            return new GraphResult();
        }

        if (type == 1 || type == 2) {
            // select twice
            List<GraphRelation> relations = relationMapper.selectByEntityIds(entityIds);
            entityIds.clear();
            for (GraphRelation relation : relations) {
                entityIds.add(relation.getSubjectId());
            }
        }

        List<GraphRelation> relations = relationMapper.selectByEntityIds(entityIds);
        Set<Integer> entityIds2 = new HashSet<>();
        for (GraphRelation relation : relations) {
            entityIds2.add(relation.getSubjectId());
            entityIds2.add(relation.getObjectId());
        }

        List<GraphEntity> entities2 = entityMapper.selectByEntityIds(entityIds2);
        Map<Integer, Map<String, String>> entityMap = new HashMap<>();
        for (GraphEntity entity : entities2) {
            int entityId = entity.getEntityId();
            if (!entityMap.containsKey(entityId)) {
                Map<String, String> node = new HashMap<>();
                node.put("id", String.valueOf(entityId));
                entityMap.put(entityId, node);
            }
            entityMap.get(entityId).put(entity.getKey(), entity.getValue());
            if ("name".equals(entity.getKey())) {
                entityMap.get(entityId).put("label", entity.getValue());
                entityMap.get(entityId).put("title", entity.getValue());
            }
            if ("class".equals(entity.getKey())) {
                if ("publication".equals(entity.getValue())) {
                    entityMap.get(entityId).put("color", "orange");
                } else if ("venue".equals(entity.getValue())) {
                    entityMap.get(entityId).put("color", "lime");
                }
            }
        }

        List<Map<String, String>> nodes = new ArrayList<>();
        for (Integer id : entityMap.keySet()) {
            nodes.add(entityMap.get(id));

        }
        GraphResult result = new GraphResult();
        result.setNodes(nodes);
        result.setLinks(relations);

        return result;
    }
}
