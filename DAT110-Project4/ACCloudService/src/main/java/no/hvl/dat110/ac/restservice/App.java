package no.hvl.dat110.ac.restservice;

import static spark.Spark.after;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.put;
import static spark.Spark.post;
import static spark.Spark.delete;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Hello world!
 *
 */
public class App {

	static AccessLog accesslog = null;
	static AccessCode accesscode = null;

	public static void main(String[] args) {

		if (args.length > 0) {
			port(Integer.parseInt(args[0]));
		} else {
			port(8080);
		}

		// objects for data stored in the service

		accesslog = new AccessLog();
		accesscode = new AccessCode();

		after((req, res) -> {
			res.type("application/json");
		});

		// for basic testing purposes
		get("/accessdevice/hello", (req, res) -> {

			Gson gson = new Gson();

			return gson.toJson("IoT Access Control Device");
		});

		// TODO: implement the routes required for the access control service

		get("/accessdevice/log", (req, res) -> {
			return accesslog.toJson();
		});

		post("accessdevice/log", (req, res) -> {
			Gson gson = new Gson();
			JsonObject jsonobject = gson.fromJson(req.body(), JsonObject.class);
			String msg = jsonobject.get("message").getAsString();
			int id = accesslog.add(msg);
			return gson.toJson(accesslog.get(id));
		});
		put("accessdevice/code", (req, res) -> {
			Gson gson2 = new Gson();

			AccessCode acscd = gson2.fromJson(req.body(), AccessCode.class);
			accesscode.setAccesscode(acscd.getAccesscode());

			return req.body();
		});
		get("/accessdevice/log/:id", (req, res) -> {
			int id1 = -1;
			try {
				id1 = Integer.parseInt(req.params(":id"));
			} catch (Exception ex) {
				
				System.out.println("Not a valid ID. Please try again!");
				System.out.println("See Stack Trace below for info");
				ex.printStackTrace();
				
			}
			Gson gson1 = new Gson();

			return gson1.toJson(accesslog.get(id1));
		});

		delete("/accessdevice/log", (req, res) -> {

			// should clear log
			accesslog.clear();
			// and return log in json
			return accesslog.toJson();
		});
		get("accessdevice/code", (req, res) -> {
			Gson gson = new Gson();
			// should return accesscode in json
			return gson.toJson(accesscode);
		});

	}

}

