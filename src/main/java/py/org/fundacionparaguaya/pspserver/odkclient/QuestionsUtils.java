package py.org.fundacionparaguaya.pspserver.odkclient;

import com.fasterxml.jackson.databind.JsonNode;
import org.opendatakit.aggregate.odktables.rest.entity.DataKeyValue;
import org.opendatakit.aggregate.odktables.rest.entity.Row;
import org.opendatakit.aggregate.odktables.rest.entity.RowList;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by rodrigovillalba on 9/4/17.
 */
public class QuestionsUtils {


    public static RowList getRowsFromQuestions(String dataETag, Map<String, SurveyQuestion> questions, List<DataKeyValue> values) {
        Row row = new Row();
        row.setValues(getValues(questions, values));
        row.setSavepointTimestamp(Instant.now().toString());

        ArrayList<Row> rows = new ArrayList<>();
        rows.add(row);
        RowList list = new RowList(rows, dataETag);
        return list;
    }

    private static ArrayList<DataKeyValue> getValues(Map<String, SurveyQuestion> questions, List<DataKeyValue> values) {

        return values.stream()
                .filter(v -> questions.containsKey(v.column))
                .collect(Collectors.toCollection(ArrayList::new));

    }

    public static Map<String, SurveyQuestion> getSurveyQuestionMap(JsonNode rootNode)
    {
        Map<String, SurveyQuestion> surveyQuestionMap = new HashMap<String, SurveyQuestion>();
        final JsonNode xlsNode = rootNode.path("xlsx");
        Iterator<JsonNode> surveyLevelChildren = xlsNode.iterator();
        while (surveyLevelChildren.hasNext()) {
            JsonNode surveyLevelChild = surveyLevelChildren.next();
            Iterator<JsonNode> questionLevelChildren = surveyLevelChild.iterator();
            while (questionLevelChildren.hasNext()) {
                JsonNode questionLevelChild = questionLevelChildren.next();
                List<JsonNode> questionNodes = getQuestionNodes(questionLevelChild);
                for (final JsonNode questionNode : questionNodes) {
                    SurveyQuestion surveyQuestion = new SurveyQuestion();
                    surveyQuestion.setName(getTextNullSafe(questionNode, "name", "_ERROR_DEFAULT"));
                    surveyQuestion.setDisplayText(getDisplayTextNullSafe(questionNode));
                    surveyQuestion.setType(getTextNullSafe(questionNode, "type", ""));
                    surveyQuestion.setRowNum(getIntNullSafe(questionNode, "_row_num"));
                    surveyQuestionMap.put(surveyQuestion.getName(), surveyQuestion);
                }
            }
        }
        return surveyQuestionMap;
    }

    /**
     * Search deeply for nodes that are probably questions (with name and display nodes)
     *
     * @param surveyNode
     * @return
     */
    private static List<JsonNode> getQuestionNodes(JsonNode surveyNode) {
        List<JsonNode> result = new ArrayList<JsonNode>();
        List<JsonNode> nodesToVisit = new ArrayList<JsonNode>();
        nodesToVisit.add(surveyNode);
        while (!nodesToVisit.isEmpty()) {
            JsonNode currentNode = nodesToVisit.remove(0);
            JsonNode nameNode = currentNode.get("name");
            JsonNode displayTextNode = currentNode.get("display");
            if (nameNode == null || nameNode.isNull() || displayTextNode == null
                    || displayTextNode.isNull()) {
                Iterator<JsonNode> children = currentNode.iterator();
                while (children.hasNext()) {
                    JsonNode child = children.next();
                    nodesToVisit.add(child);
                }
            } else {
                result.add(currentNode);
            }
        }
        return result;
    }



    private static int getIntNullSafe(JsonNode node, String path) {
        int result = 0;
        if (node.findPath(path) != null && !node.findPath(path).isNull()) {
            result = node.findPath(path).asInt();
        }
        return result;
    }

   private static String getTextNullSafe(JsonNode node, String path, String defaultText) {
        String result = "";
        if (node.findPath(path) == null || node.findPath(path).isNull()) {
            result = defaultText;
        } else {
            result = node.findPath(path).asText();
        }
        return result;
    }

    private static String getDisplayTextNullSafe(JsonNode node) {
        String result = "";
        JsonNode displayNode = node.findPath("display");

        if (displayNode == null || displayNode.isNull()) {
            result = "";
        } else {
            JsonNode textNode = displayNode.get("text");
            JsonNode imageNode = displayNode.get("image");
            if (textNode != null && !textNode.isNull()) {
                JsonNode defaultNode = textNode.get("default");
                if (defaultNode != null && !defaultNode.isNull()) {
                    result = defaultNode.asText();
                    Iterator<Map.Entry<String, JsonNode>> children = textNode.fields();
                    while (children.hasNext()) {
                        Map.Entry<String, JsonNode> entry = (Map.Entry<String, JsonNode>) children.next();
                        if (!entry.getKey().equalsIgnoreCase("default")) {
                            JsonNode childNode = entry.getValue();
                            if (childNode != null && !childNode.isNull()) {
                                result = result + " / " + childNode.asText();
                            }
                        }
                    }
                } else {
                    result = textNode.asText();
                }
            } else if (imageNode != null && !imageNode.isNull()) {
                result = imageNode.asText();
            } else {
                result = "";
            }
        }
        return result;
    }


}
