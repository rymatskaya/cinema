package senla.controller;

import lombok.SneakyThrows;
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
import senla.util.PasswordsHider;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.io.*;

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

            System.out.println("""
                    Добро пожаловать!
                    1 - регистрация
                    2 - вход
                    3 - просмотр мероприятий
                    0 - выйти
                    """);
            System.out.print("Выберите пункт меню: ");
            String stap = scanner.nextLine();

            switch (stap) {
                case "1" -> {
                    System.out.println("Меню регистрации");
                    System.out.println("Введите логин:");
                    String username = scanner.nextLine();
                    System.out.println("Введите пароль:");
                    String password = scanner.nextLine();
                    System.out.println("Введите email:");
                    String email = scanner.nextLine();
                    User user = new User(username, password, email);
                    userService.create(user);
                }
                case "2" -> {
                    System.out.println("Меню входа");
                    System.out.println("Введите логин:");
                    String username = scanner.nextLine();
                    System.out.println("Введите пароль:");
                    String password = scanner.nextLine();
//                    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//                    System.out.print("Enter username: ");
//                    String username = in.readLine();
//                    Thread hide = new PasswordsHider("Enter password: ");
//                    hide.start();
//                    String password = in.readLine();
//                    hide.interrupt();

                    System.out.println("Вывод меню в зависимости от роли");
                    user = userService.getUserByLoginPassword(username, password)
                            .orElseThrow(() -> new RuntimeException(String.format("Пользователя " +
                                    "с логином %s и паролем %s не найдено ", username, password)));

                    switch (user.getRole()) {
                        case USER -> MenuUser.menuUser();
                        case MANAGER -> MenuManager.MenuManager();
                        case ADMIN -> MenuAdmin.menuAdmin();
                    }

                }

                case "3" -> {
                    System.out.println("Просмотр мероприятий");
                    eventService.getAllEventsFull().forEach(System.out::println);
                }
                case "0" -> {
                    System.out.println("Выход");
                    return;
                }

            }
        }
    }


}
