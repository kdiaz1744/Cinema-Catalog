package com.gmail.kevin;

import com.github.appreciated.app.layout.component.menu.left.items.LeftHeaderItem;
import com.github.appreciated.app.layout.component.menu.top.TopMenuComponent;
import com.github.appreciated.app.layout.component.menu.top.builder.TopAppMenuBuilder;
import com.github.appreciated.app.layout.component.menu.top.item.TopNavigationItem;
import com.github.appreciated.app.layout.notification.component.AppBarNotificationButton;
import com.github.appreciated.app.layout.notification.entitiy.DefaultNotification;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.Route;

import com.github.appreciated.app.layout.behaviour.Behaviour;
import com.github.appreciated.app.layout.builder.AppLayoutBuilder;
import com.github.appreciated.app.layout.component.appbar.AppBarBuilder;
import com.github.appreciated.app.layout.entity.DefaultBadgeHolder;
import com.github.appreciated.app.layout.notification.DefaultNotificationHolder;
import com.github.appreciated.app.layout.router.AppLayoutRouterLayout;

import static com.github.appreciated.app.layout.entity.Section.FOOTER;
import static com.github.appreciated.app.layout.entity.Section.HEADER;


@Push
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
public class MainView extends AppLayoutRouterLayout {

    private DefaultNotificationHolder notifications = new DefaultNotificationHolder();
    private DefaultBadgeHolder badge = new DefaultBadgeHolder(5);

    public MainView() {
        notifications.addClickListener(notification -> System.out.println(notification.getTitle()));
        //notifications.addNotification(new DefaultNotification("title","description"));

        //TopNavigationItem menuEntry2 = new TopNavigationItem("Menu2", VaadinIcon.MENU.create(), View8.class);
        //LeftNavigationItem menuEntry = new LeftNavigationItem("Menu", VaadinIcon.MENU.create(), View8.class);
        //badge.bind(menuEntry.getBadge());

        init(AppLayoutBuilder
                .get(Behaviour.TOP_LARGE)
                .withIcon("/icons/logo.png")
                .withTitle("Cinema Movies")
                //.withIconComponent(new Icon(VaadinIcon.))
                .withAppBar(AppBarBuilder
                        .get()
                        //.add(new LeftHeaderItem("Dashboard", "Version 2.4.0", "/icons/Movie Catalog-logo.png"))
                        .add(new AppBarNotificationButton<>(VaadinIcon.BELL, notifications))
                        .build())
                .withAppMenu(TopAppMenuBuilder
                        .get()
                        .add(new TopNavigationItem("Movie Earnings", VaadinIcon.CHART_TIMELINE.create(), earnings.class))
                        .add(new TopNavigationItem("Movie Catalog", VaadinIcon.BOOK.create(), View6.class))
                        .add(new TopNavigationItem("Home", VaadinIcon.HOME.create(), Hello.class))
                        .add(new TopNavigationItem("FeedBack", VaadinIcon.COMMENT.create(), First.class))
                        .addToSection(new TopNavigationItem("Exit", VaadinIcon.EXIT_O.create(), Login.class), HEADER)
                        .build())
                .build()
        );

    }
}
