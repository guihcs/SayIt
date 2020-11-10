package com.sayit.ui.navigator;

import javafx.stage.Stage;

import java.util.function.Consumer;

public class RenderedRoute {

    private final String path;
    private final RouteType routeType;
    private Stage modal;
    private Consumer<Object> consumer;
    private Object param;


    public RenderedRoute(String path, RouteType routeType) {
        this.path = path;
        this.routeType = routeType;
    }

    public RenderedRoute(String path, RouteType routeType, Stage modal, Consumer<Object> consumer) {
        this.path = path;
        this.routeType = routeType;
        this.modal = modal;
        this.consumer = consumer;
    }

    public RenderedRoute(String path, RouteType routeType, Stage modal, Consumer<Object> consumer, Object param) {
        this.path = path;
        this.routeType = routeType;
        this.modal = modal;
        this.consumer = consumer;
        this.param = param;
    }


    public RenderedRoute(String path, RouteType routeType, Consumer<Object> consumer) {
        this.path = path;
        this.routeType = routeType;
        this.consumer = consumer;
    }

    public RenderedRoute(String path, RouteType routeType, Consumer<Object> consumer, Object param) {
        this.path = path;
        this.routeType = routeType;
        this.consumer = consumer;
        this.param = param;
    }

    public String getPath() {
        return path;
    }

    public RouteType getRouteType() {
        return routeType;
    }

    public Stage getModal() {
        return modal;
    }

    public Consumer<Object> getConsumer() {
        return consumer;
    }

    public Object getParam() {
        return param;
    }


    @Override
    public String toString() {
        return "RenderedRoute{" +
                "path='" + path + '\'' +
                ", routeType=" + routeType +
                ", modal=" + modal +
                ", consumer=" + consumer +
                ", param=" + param +
                '}';
    }
}
