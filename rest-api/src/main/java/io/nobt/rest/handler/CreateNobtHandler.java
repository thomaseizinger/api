/**
 * 
 */
package io.nobt.rest.handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.nobt.core.domain.Nobt;
import io.nobt.persistence.NobtDao;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * @author Matthias
 *
 */
public class CreateNobtHandler implements Route {

	private NobtDao nobtDao;
	private Gson gson;
	private JsonParser parser;

	public CreateNobtHandler(NobtDao nobtDao, Gson gson, JsonParser parser) {
		this.nobtDao = nobtDao;
		this.gson = gson;
		this.parser = parser;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see spark.Route#handle(spark.Request, spark.Response)
	 */
	@Override
	public Object handle(Request req, Response resp) throws Exception {
		JsonObject o = parser.parse(req.body()).getAsJsonObject();
		Nobt nobt = nobtDao.create(o.get("nobtName").getAsString());
		resp.header("Location", req.url() + "/" + nobt.getId());
		return gson.toJson(nobt);
	}

}
