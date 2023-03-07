package controllers;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class ServerStatusController extends Controller {


  public Result status() {
    return ok(Json.newObject().put("status", "Up and running"));

  }

}
