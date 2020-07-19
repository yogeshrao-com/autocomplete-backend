package com.autocomplete.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.autocomplete.model.Course;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class CourseServiceImpl implements CourseService {

	/*@Autowired
    private RestHighLevelClient client;*/
	
	@Value("${elasticsearch.host.url}")
    private String elasticsearchHost;
	
	final ObjectMapper objectMapper = new ObjectMapper();
	

    public String createCourse(Course course) throws Exception {
    	

        UUID uuid = UUID.randomUUID();
        course.setId(uuid.toString());

        System.out.println(uuid.toString());
        
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("id", course.getId());
        jsonMap.put("instructor", course.getInstructor());
        jsonMap.put("topic", course.getTopic());
        jsonMap.put("subject", course.getSubject());
        jsonMap.put("title", course.getTitle());
        
        IndexRequest indexRequest = new IndexRequest("posts")
            .id(course.getId()).source(jsonMap);
        
        @SuppressWarnings("resource")
		RestHighLevelClient client = new RestHighLevelClient(
             RestClient.builder(new HttpHost(elasticsearchHost, 443, "https")));
        
        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);

        return indexResponse
                .getResult()
                .name();
    }
    
    
    public Course getById(String id) throws Exception {

        GetRequest getRequest = new GetRequest("posts", id);  
        
        @SuppressWarnings("resource")
		RestHighLevelClient client = new RestHighLevelClient(
             RestClient.builder(new HttpHost(elasticsearchHost, 443, "https")));

        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        Map<String, Object> resultMap = getResponse.getSource();
        
        return objectMapper
                .convertValue(resultMap, Course.class);


    }
    
    
    public List<Course> searchByInstructor(String instructor) throws Exception {

        SearchRequest searchRequest = new SearchRequest();
        
        
        QueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("instructor", instructor);
        
        //QueryBuilder matchQueryBuilder = QueryBuilders.wildcardQuery("instructor", instructor+".*");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
        
        /*BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
                .must(QueryBuilders.regexpQuery("instructor", instructor +".*"));*/
        
        /*searchSourceBuilder.query(boolQuery)*/;
        System.out.println("print instructor here: "+ instructor);
        
        sourceBuilder.query(QueryBuilders.termQuery("instructor", matchQueryBuilder)); 
        
        QueryStringQueryBuilder queryBuilder = QueryBuilders.queryStringQuery(instructor+"*").defaultField("instructor");
        
        sourceBuilder.query(queryBuilder);
        
        searchRequest.source(sourceBuilder);         
        
        @SuppressWarnings("resource")
		RestHighLevelClient client = new RestHighLevelClient(
             RestClient.builder(new HttpHost(elasticsearchHost, 443, "https")));

        SearchResponse response =
                client.search(searchRequest, RequestOptions.DEFAULT); 
        

        System.out.println(response.toString());
        
        SearchHit[] searchHit = response.getHits().getHits();

        List<Course> courses = new ArrayList<>();

        if (searchHit.length > 0) {

            Arrays.stream(searchHit)
                    .forEach(hit -> courses
                            .add(objectMapper
                                    .convertValue(hit.getSourceAsMap(),
                                                    Course.class))
                    );
        }

        return courses;
        
    }
    
   
}
