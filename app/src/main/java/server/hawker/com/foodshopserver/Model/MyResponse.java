package server.hawker.com.foodshopserver.Model;

import java.util.List;

public class MyResponse {
    public long mutlicast_id;
    public int success,failure,canonical_ids;
    public List<Result> results;
}
