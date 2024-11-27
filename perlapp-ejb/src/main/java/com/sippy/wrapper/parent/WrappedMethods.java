package com.sippy.wrapper.parent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sippy.wrapper.parent.database.DatabaseConnection;
import com.sippy.wrapper.parent.request.JavaTestRequest;
import com.sippy.wrapper.parent.response.JavaTestResponse;
import java.util.*;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class WrappedMethods {

  private static final Logger LOGGER = LoggerFactory.getLogger(WrappedMethods.class);

  @EJB DatabaseConnection databaseConnection;

  @RpcMethod(name = "javaTest", description = "Check if everything works :)")
  public Map<String, Object> javaTest(JavaTestRequest request) {
    JavaTestResponse response = new JavaTestResponse();

    int count = databaseConnection.getAllTnbs().size();

    LOGGER.info("the count is: " + count);

    response.setId(request.getId());
    String tempFeeling = request.isTemperatureOver20Degree() ? "warm" : "cold";
    response.setOutput(
        String.format(
            "%s has a rather %s day. And he has %d tnbs", request.getName(), tempFeeling, count));

    Map<String, Object> jsonResponse = new HashMap<>();
    jsonResponse.put("faultCode", "200");
    jsonResponse.put("faultString", "Method success");
    jsonResponse.put("something", response);

    return jsonResponse;
  }

  //Zeit leider ausgegangen, aber Perl ist ehrlich dreck

  @RpcMethod(name = "getTnbList", description = "mimics perl Method")
  public HashMap<String, Object> getTnbList (final String params){
    final var tnbs_from_db = databaseConnection.getAllTnbs();
    //final var tnb = tnbs_from_db.stream().filter(e -> e.getTnb().equals(params));
    /*
    final var tnbs = tnbs_from_db
            .stream()
            .filter(e->true)
            .sorted();
    */

    final var response = new HashMap<String, Object>();
    response.put("faultCode", "200");
    response.put("faultString", "Method success");
    response.put("tnbs", tnbs_from_db);
    return response;
  }
}
