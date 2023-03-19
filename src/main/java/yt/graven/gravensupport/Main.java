package yt.graven.gravensupport;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import yt.graven.gravensupport.configuration.BotConfig;
import yt.graven.gravensupport.database.DatabaseManager;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(BotConfig.class);

        Startup startup = context.getBean(Startup.class);
        startup.run();
    }

    @Component
    @RequiredArgsConstructor
    private static class Startup {

        private final EventReceiver eventReceiver;
        private final DatabaseManager dbManager;
        private final JDA client;

        public void run() {
            dbManager.createAll();

            this.client
                    .getPresence()
                    .setPresence(Activity.listening("/ticket | Ouvrez un ticket avec la modération"), false);

            this.client.addEventListener(eventReceiver);
        }
    }
}
