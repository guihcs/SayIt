package com.sayit.ui.navigator;

public class SceneRoute {

    private String name;
    private String title;
    private String route;
    private String layout;
    private String style;

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getRoute() {
        return route;
    }

    public String getLayout() {
        return layout;
    }

    public String getStyle() {
        return style;
    }

    @Override
    public String toString() {
        return "SceneRoute{" +
                "name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", route='" + route + '\'' +
                ", layout='" + layout + '\'' +
                ", style='" + style + '\'' +
                '}';
    }
}
