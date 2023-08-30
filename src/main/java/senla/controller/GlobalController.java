package senla.controller;

import senla.model.User;
import senla.model.UserRole;
import senla.repository.UserRepository;
import senla.repository.UserRepositoryImpl;
import senla.service.UserService;
import senla.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class GlobalController {
    private static Scanner scanner = new Scanner(System.in);

    public static void start() {
        UserRepository userRepository = new UserRepositoryImpl();
        UserService userService = new UserServiceImpl(userRepository);

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
                    System.out.println("Вывод меню в зависимости от роли");
                    User user = userService.getUserByLoginPassword(username, password)
                            .orElseThrow(() -> new RuntimeException(String.format("Пользователя " +
                                    "с логином %s и паролем %s не найдено ", username, password)));

                    switch (user.getRole()) {
                        case USER -> MenuUser.menuUser();
                        case MANAGER -> System.out.println("Вывод меню менеджера");
                        case ADMIN -> MenuAdmin.menuAdmin();
                    }

                }

                case "3" -> {
                    System.out.println("Просмотр мероприятий");

                }
                case "0" -> {
                    System.out.println("Выход");
                    return;
                }

            }
        }
    }
}
