package com.sayit.ui.navigator;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sayit.di.Injector;
import com.sayit.ui.control.FXMLManager;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Consumer;

public class Navigator {

    private final Stage context;
    private static Map<String, SceneRoute> sceneRouteMap;
    private static final Stack<RenderedRoute> routeStack = new Stack<>();


    private Navigator(Stage context) {
        this.context = context;
    }


    public static Navigator of(Stage context) {
        return new Navigator(context);
    }

    public void pushNamed(String path) {
        Navigator.routeStack.push(new RenderedRoute(path, RouteType.NORMAL));

        SceneRoute sceneRoute = Navigator.sceneRouteMap.get(path);
        var loader = FXMLManager.getLoader(sceneRoute.getLayout());

        if (sceneRoute.getController() != null){
            loader.setControllerFactory(c -> {
                try {
                    Class<?> aClass = ClassLoader.getSystemClassLoader().loadClass(sceneRoute.getController());
                    Object o = aClass.getConstructor().newInstance();
                    Injector.inject(o);
                    return o;
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                return null;
            });
        } else {
            loader.setControllerFactory(Navigator::instantiateInjected);
        }



        Parent node = (Parent) FXMLManager.loadFromLoader(loader);

        if (node == null) throw new RuntimeException("Path not registered.");
        if (sceneRoute.getStyle() != null)
            Objects.requireNonNull(node).getStylesheets().add(sceneRoute.getStyle());

        if (sceneRoute.getTitle() != null)
            context.setTitle(sceneRoute.getTitle());

        Platform.runLater(() -> {
            context.setScene(new Scene(node));
            context.show();
            centerWindow();
        });
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


    private void centerWindow() {
        var screenBounds = Screen.getPrimary().getVisualBounds();
        context.setX(screenBounds.getMaxX() * 0.5 - context.getWidth() * 0.5);
        context.setY(screenBounds.getMaxY() * 0.5 - context.getHeight() * 0.5);
    }

    public void pushNamedModal(String path, int width, int height, Consumer<Object> resultCallback) {
        Stage requestWindow = new Stage();

        Navigator.routeStack.push(new RenderedRoute(path, RouteType.MODAL, requestWindow, resultCallback));

        SceneRoute sceneRoute = Navigator.sceneRouteMap.get(path);
        var loader = FXMLManager.getLoader(sceneRoute.getLayout());
        loader.setControllerFactory(Navigator::instantiateInjected);
        Parent node = (Parent) FXMLManager.loadFromLoader(loader);
        Objects.requireNonNull(node).getStylesheets().add(sceneRoute.getStyle());

        requestWindow.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(node, width, height);
        requestWindow.setScene(scene);
        if (sceneRoute.getTitle() != null)
            requestWindow.setTitle(sceneRoute.getTitle());


        requestWindow.showAndWait();
    }


    public void pushNamedModal(String path, int width, int height, Object param, Consumer<Object> resultCallback) {
        Stage requestWindow = new Stage();
        Navigator.routeStack.push(new RenderedRoute(path, RouteType.MODAL, requestWindow, resultCallback, param));

        SceneRoute sceneRoute = Navigator.sceneRouteMap.get(path);
        var loader = FXMLManager.getLoader(sceneRoute.getLayout());
        loader.setControllerFactory(Navigator::instantiateInjected);
        Parent node = (Parent) FXMLManager.loadFromLoader(loader);
        Objects.requireNonNull(node).getStylesheets().add(sceneRoute.getStyle());
        Object controller = loader.getController();

        if (controller instanceof Configurable) {
            ((Configurable) controller).configure(param);
        }

        requestWindow.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(node, width, height);
        requestWindow.setScene(scene);
        if (sceneRoute.getTitle() != null)
            requestWindow.setTitle(sceneRoute.getTitle());


        requestWindow.showAndWait();
    }

    public static Node buildNamed(String path, Consumer<Object> resultCallback) {
        Navigator.routeStack.push(new RenderedRoute(path, RouteType.BUILD, resultCallback));
        SceneRoute sceneRoute = Navigator.sceneRouteMap.get(path);
        var loader = FXMLManager.getLoader(sceneRoute.getLayout());
        loader.setControllerFactory(Navigator::instantiateInjected);
        Parent node = (Parent) FXMLManager.loadFromLoader(loader);
        if (sceneRoute.getStyle() != null)
            Objects.requireNonNull(node).getStylesheets().add(sceneRoute.getStyle());


        return node;
    }


    public static Node buildNamed(String path, Object param, Consumer<Object> resultCallback) {
        Navigator.routeStack.push(new RenderedRoute(path, RouteType.BUILD, resultCallback, param));

        SceneRoute sceneRoute = Navigator.sceneRouteMap.get(path);
        var loader = FXMLManager.getLoader(sceneRoute.getLayout());
        loader.setControllerFactory(Navigator::instantiateInjected);
        Parent node = (Parent) FXMLManager.loadFromLoader(loader);
        if (sceneRoute.getStyle() != null)
            Objects.requireNonNull(node).getStylesheets().add(sceneRoute.getStyle());

        Object controller = loader.getController();

        if (controller instanceof Configurable) {
            ((Configurable) controller).configure(param);
        }


        return node;
    }


    public static Node buildNamed(String path) {
        Navigator.routeStack.push(new RenderedRoute(path, RouteType.BUILD));

        SceneRoute sceneRoute = Navigator.sceneRouteMap.get(path);
        var loader = FXMLManager.getLoader(sceneRoute.getLayout());
        loader.setControllerFactory(Navigator::instantiateInjected);
        Parent node = (Parent) FXMLManager.loadFromLoader(loader);
        if (sceneRoute.getStyle() != null)
            Objects.requireNonNull(node).getStylesheets().add(sceneRoute.getStyle());


        return node;
    }

    public void pop() {
        Navigator.routeStack.pop();
        RenderedRoute peek = Navigator.routeStack.peek();
        if (peek.getRouteType() == RouteType.NORMAL) {
            Navigator.routeStack.pop();
            pushNamed(peek.getPath());
        }

    }

    public <T> void popResult(T result) {

        RenderedRoute lastRoute = Navigator.routeStack.pop();

        if (lastRoute.getModal() != null) lastRoute.getModal().close();
        if (lastRoute.getConsumer() != null) lastRoute.getConsumer().accept(result);

        if (lastRoute.getRouteType() == RouteType.NORMAL
                &&
                Navigator.routeStack.peek().getRouteType() == RouteType.NORMAL) {
            rebuildLastRoute();
        }

    }

    public void rebuildLastRoute() {
        pushNamed(Navigator.routeStack.pop().getPath());
    }

    public static void loadFrom(String path) {
        InputStream resource = Navigator.class.getClassLoader().getResourceAsStream(path);
        if (resource == null) throw new RuntimeException("Resource not found.");
        Reader reader = new InputStreamReader(resource);

        Type listOfMyClassObject = new TypeToken<ArrayList<SceneRoute>>() {
        }.getType();

        List<SceneRoute> sceneRoutes = new Gson().fromJson(reader, listOfMyClassObject);
        Map<String, SceneRoute> sceneRouteMap = new HashMap<>();

        sceneRoutes.forEach(r -> sceneRouteMap.put(r.getRoute(), r));

        Navigator.sceneRouteMap = sceneRouteMap;
    }

    public static void clearStack() {
        Navigator.routeStack.clear();
    }
}
