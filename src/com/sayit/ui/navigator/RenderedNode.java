package com.sayit.ui.navigator;

import com.sayit.di.Injector;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class RenderedNode {

    private final SceneRoute sceneRoute;
    private final Parent parent;
    private final Object controller;

    public RenderedNode(SceneRoute sceneRoute) {
        this.sceneRoute = sceneRoute;

        FXMLLoader loader = getLoader(sceneRoute.getLayout());

        if (sceneRoute.getController() != null) {
            loader.setControllerFactory(c -> getObject(sceneRoute));
        } else {
            loader.setControllerFactory(RenderedNode::instantiateInjected);
        }

        parent = (Parent) RenderedNode.loadFromLoader(loader);

        if (parent == null) throw new RuntimeException("Path not registered.");
        if (sceneRoute.getStyle() != null)
            Objects.requireNonNull(parent).getStylesheets().add(sceneRoute.getStyle());

        controller = loader.getController();
    }


    public RenderedNode(SceneRoute sceneRoute, Object param) {
        this(sceneRoute);

        if (controller instanceof Configurable) {
            ((Configurable) controller).configure(param);
        }
    }


    private static FXMLLoader getLoader(String path) {
        return new FXMLLoader(RenderedNode.class.getResource(path));
    }

    public static Node loadFromLoader(FXMLLoader loader) {
        try {
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static Object getObject(SceneRoute sceneRoute) {
        try {
            Class<?> aClass = ClassLoader.getSystemClassLoader().loadClass(sceneRoute.getController());
            Object o = aClass.getConstructor().newInstance();
            Injector.inject(o);
            return o;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Object instantiateInjected(Class<?> type) {
        try {
            Object o = type.getConstructor().newInstance();
            Injector.inject(o);
            return o;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }


    public SceneRoute getSceneRoute() {
        return sceneRoute;
    }

    public Parent getParent() {
        return parent;
    }

    public Object getController() {
        return controller;
    }
}
