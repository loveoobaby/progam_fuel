package com.yss.vert.jdbc;

import io.vertx.core.*;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.asyncsql.MySQLClient;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.sql.UpdateResult;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;

public class JdbcVerticle extends AbstractVerticle {

    private Logger logger = LoggerFactory.getLogger(JdbcVerticle.class);

    private JDBCClient jdbcClient;

    private JsonObject config = new JsonObject()
            .put("url", "jdbc:hsqldb:file:db/whiskies")
            .put("driver_class", "org.hsqldb.jdbcDriver")
            .put("max_pool_size", 30);

    public static void main(String[] args) {
        Vertx.vertx().deployVerticle("com.yss.vert.jdbc.JdbcVerticle", r->{
            if(r.failed()){
                System.out.println(r.cause());
                r.cause().printStackTrace();
                System.exit(-1);
            }
        });
    }

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        jdbcClient = JDBCClient.createShared(vertx, config, "jdbc");

        Future<Void> fut1 = Future.future();
        startWebApp((http) -> completeStartup(http, fut1));

        fut1.compose(v -> {
                    jdbcClient.getConnection(ar -> {
                        if (ar.failed()) {
                            startFuture.fail(ar.cause());
                        } else {
                            initDBData(Future.succeededFuture(ar.result()), startFuture);
                        }
                    });
                }
                , startFuture);

    }

    private void startBackend(Future<Void> fut) {
        jdbcClient.getConnection(ar -> {
            if (ar.failed()) {
                fut.fail(ar.cause());
            } else {
                initDBData(Future.succeededFuture(ar.result()), fut);
            }
        });
    }

    private void initDBData(AsyncResult<SQLConnection> result, Future<Void> fut) {
        if (result.failed()) {
            fut.fail(result.cause());
        } else {
            SQLConnection connection = result.result();
            connection.execute(
                    "CREATE TABLE IF NOT EXISTS Whisky (id INTEGER IDENTITY, name varchar(100), origin varchar" +
                            "(100))",
                    ar -> {
                        if (ar.failed()) {
                            fut.fail(ar.cause());
                            connection.close();
                            return;
                        }
                        connection.query("SELECT * FROM Whisky", select -> {
                            if (select.failed()) {
                                fut.fail(ar.cause());
                                connection.close();
                                return;
                            }
                            if (select.result().getNumRows() == 0) {
                                insert(
                                        new Whisky("Bowmore 15 Years Laimrig", "Scotland, Islay"), connection,
                                        (v) -> insert(new Whisky("Talisker 57° North", "Scotland, Island"), connection,
                                                (r) -> {
                                                    connection.close();
                                                }));
                            } else {
                                connection.close();
                            }
                        });

                    });
        }
    }

    private void insert(Whisky whisky, SQLConnection connection, Handler<AsyncResult<Whisky>> next) {
        String sql = "INSERT INTO Whisky (name, origin) VALUES ?, ?";
        connection.updateWithParams(sql,
                new JsonArray().add(whisky.getName()).add(whisky.getOrigin()),
                (ar) -> {
                    if (ar.failed()) {
                        next.handle(Future.failedFuture(ar.cause()));
                        connection.close();
                        return;
                    }
                    UpdateResult result = ar.result();
                    // Build a new whisky instance with the generated id.
                    Whisky w = new Whisky(result.getKeys().getInteger(0), whisky.getName(), whisky.getOrigin());
                    next.handle(Future.succeededFuture(w));
                });
    }

    private void startWebApp(Handler<AsyncResult<HttpServer>> next) {
        // Create a router object.
        Router router = Router.router(vertx);

        // Bind "/" to our hello message.
        router.route("/").handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            response
                    .putHeader("content-type", "text/html")
                    .end("<h1>Hello from my first Vert.x 3 application</h1>");
        });


//        // Create the HTTP server and pass the "accept" method to the request handler.
//        vertx.createHttpServer()
//                .requestHandler(router)
//                .listen(8080, "localhost", res ->{
//                    if(res.failed()){
//                        System.out.println(res.cause());
//                    }else{
//                        System.out.println("1111111");
//                    }
//                });

        vertx.createHttpServer()
                .requestHandler(router)
                .listen(8080, "localhost", next::handle);
    }

    private void completeStartup(AsyncResult<HttpServer> http, Future<Void> fut) {
        logger.info("1111111");
        if (http.succeeded()) {
            fut.complete();
        } else {
            fut.fail(http.cause());
        }
    }
}
