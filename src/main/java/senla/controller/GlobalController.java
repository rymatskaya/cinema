package senla.controller;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import senla.controller.helper.Constants;
import senla.model.User;
import senla.model.UserRole;
import senla.repository.EventRepository;
import senla.repository.EventRepositoryImpl;
import senla.repository.UserRepository;
import senla.repository.UserRepositoryImpl;
import senla.service.EventService;
import senla.service.EventServiceImpl;
import senla.service.UserService;
import senla.service.UserServiceImpl;

import java.util.*;

@Slf4j

public class GlobalController {
    private static Scanner scanner = new Scanner(System.in);

    public static User user;

    @SneakyThrows
    public static void start() {
        UserRepository userRepository = new UserRepositoryImpl();
        UserService userService = new UserServiceImpl(userRepository);
        EventRepository eventRepository = new EventRepositoryImpl();
        EventService eventService = new EventServiceImpl(eventRepository);

        while (true) {

            System.out.println(Constants.MAIN_MENU);
            log.info("Вывод главного меню");
            System.out.print(Constants.CHOISE);
            String stap = scanner.nextLine();

            switch (stap) {
                case "1" -> {
                    log.info(Constants.REGISTRATION_MENU);
                    System.out.println(Constants.REGISTRATION_MENU);
                    System.out.println(Constants.INPUT_LOGIN);
                    String username = scanner.nextLine();
                    System.out.println(Constants.INPUT_PASSWORD);
                    String password = scanner.nextLine();
                    System.out.println(Constants.INPUT_EMAIL);
                    String email = scanner.nextLine();
                    User user = new User(username, password, email);
                    try {
                        if (userService.create(user)) {
                            log.info("Пользователь с логином {} успешно создан", username);
                        } else {
                            log.error("Не удалось создать пользователя с логином {}", username);
                        }
                    } catch (RuntimeException e) {
                        log.error("Регистрация не работает. Обратитесь к администратору системы");
                    }
                }
                case "2" -> {
                    log.info(Constants.INPUT_MENU);
                    System.out.println(Constants.INPUT_MENU);
                    System.out.println(Constants.INPUT_LOGIN);
                    String username = scanner.nextLine();
                    System.out.println(Constants.INPUT_PASSWORD);
                    String password = scanner.nextLine();

                    user = userService.getUserByLoginPassword(username, password)
                            .orElseThrow(() -> new RuntimeException(String.format("Пользователя " +
                                    "с логином %s и паролем %s не найдено ", username, password)));
                    try {
                        if (user != null) {
                            log.info("Пользователь с логином {} успешно авторизован", username);
                        } else {
                            log.error("Пользователь с логином {} не существует", username);
                        }
                    } catch (RuntimeException e) {
                        log.error("Авторизация не работает. Обратитесь к администратору системы");
                    }

                    switch (user.getRole()) {
                        case USER -> MenuUser.menuUser();
                        case MANAGER -> MenuManager.MenuManager();
                        case ADMIN -> MenuAdmin.menuAdmin();
                    }
                }
                case "3" -> {
                    System.out.println(Constants.VIEW_EVENTS);
                    log.info(Constants.VIEW_EVENTS);
                    eventService.getAllEventsFull().forEach(System.out::println);
                }
                case "0" -> {
                    System.out.println(Constants.EXIT);
                    return;
                }

            }
        }
    }


}
