package senla.controller;

import senla.model.*;
import senla.repository.*;
import senla.service.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class MenuAdmin {
    private static Scanner scanner = new Scanner(System.in);

    public static void menuAdmin() {
        UserRepository userRepository = new UserRepositoryImpl();
        UserService userService = new UserServiceImpl(userRepository);
        MovieRepository movieRepository = new MovieRepositoryImpl();
        MovieService movieService = new MovieServiceImpl(movieRepository);
        EventRepository eventRepository = new EventRepositoryImpl();
        EventService eventService = new EventServiceImpl(eventRepository);
        TicketRepository ticketRepository = new TicketRepositoryImpl();
        TicketService ticketService = new TicketServiceImpl(ticketRepository);

        while (true) {
            String str = """
                    Пользователи
                     1 - Добавить пользователя
                     2 - Изменить пользователя
                     3 - Удалить пользователя
                     4 - Просмотр пользователей
                     Фильмы
                     5 - Добавить фильм
                     6 - Изменить фильм
                     7 - Удалить фильм
                     8 - Просмотр фильмов
                     Сеанс
                     9 - Добавить сеанс
                     10 - Изменить сеанс
                     11 - Удалить сеанс
                     12 - Просмотр мероприятий
                     Билеты
                     13 - Добавить билет
                     14 - Изменить билет
                     15 - Удалить билет
                     16 - Просмотреть все билеты
                     
                     0 - Вернуться в главное меню                   
                                        
                    """;
            System.out.println(str);
            System.out.print("Выберите вариант меню: ");
            String step = scanner.nextLine();

            if (step.equals("1")) {
                System.out.println("Добавление пользователя");
                addUser(userService);

            } else if (step.equals("2")) {
                System.out.println("Изменение пользователя");
                updateUser(userService);

            } else if (step.equals("3")) {
                System.out.println("Удаление пользователя");
                deleteUser(userService);

            } else if (step.equals("4")) {
                System.out.println("Просмотр пользователей");
                userService.getAll().forEach(System.out::println);

            } else if (step.equals("5")) {
                System.out.println("Добавление фильма");
                System.out.println("Введите название фильма:");
                String title = scanner.nextLine();
                Movie movie = new Movie(title);
                movieService.createMovie(movie);

            } else if (step.equals("6")) {
                System.out.println("Изменение фильма");
                System.out.println("Введите id фильма для изменения:");
                Integer id = Integer.valueOf(scanner.nextLine()) ;

                Movie movie = movieService.getMovieById(id)
                        .orElseThrow(() -> new RuntimeException(String.format("Фильма " +
                                "с id= %s не найдено ", id)));

                System.out.println("Введите название фильма:");
                String title = scanner.nextLine();
                if (title != null) {
                    movieService.updateMovie(movie.getMovieId(), title);
                }
            } else if (step.equals("7")) {
                System.out.println("Удаление фильма");
                System.out.println("Введите id фильма для удаления:");
                Integer id = Integer.valueOf(scanner.nextLine()) ;

                Movie movie = movieService.getMovieById(id)
                        .orElseThrow(() -> new RuntimeException(String.format("Фильма " +
                                "с id= %s не найдено ", id)));

                movieService.deleteMovie(movie.getMovieId());
            } else if (step.equals("8")) {
                System.out.println("Просмотр фильмов");
                movieService.getAllMovies().forEach(System.out::println);
            } else if (step.equals("9")) {
                System.out.println("Добавление сеанса");
                addEvent(movieService, eventService);
            } else if (step.equals("10")) {
                System.out.println("Изменение сеанса");
                System.out.println("Введите id сеанса для изменения:");
                Integer id = Integer.valueOf(scanner.nextLine()) ;

                Event event= eventService.getEventById(id)
                        .orElseThrow(() -> new RuntimeException(String.format("Сеанса " +
                                "с id= %s не найдено ", id)));


                System.out.println("Введите время сеанса:");
                Integer DD;
                do {
                    System.out.println("Число:");
                    DD = Integer.valueOf(scanner.nextLine()) ;
                } while (DD<1 || DD>31);
                Integer MM;
                do {
                    System.out.println("Месяц:");
                    MM = Integer.valueOf(scanner.nextLine());
                } while (MM<1 || MM>12);
                Integer YYYY;
                do {
                    System.out.println("Год:");
                    YYYY = Integer.valueOf(scanner.nextLine()) ;
                } while (YYYY<2023 || YYYY>3000);
                Integer HH24;
                do {
                    System.out.println("Время (часы) от 0 до 23 ");
                    HH24 = Integer.valueOf(scanner.nextLine()) ;
                } while (HH24<0 || HH24>23);
                Integer MI;
                do {
                    System.out.println("Время (минуты) от 0 до 59 ");
                    MI = Integer.valueOf(scanner.nextLine()) ;
                } while (MI<0 || MI>59);
                LocalDateTime MovieDateTime = null;
                try{
                    MovieDateTime = LocalDateTime.of(YYYY, MM, DD, HH24, MI);
                } catch ( DateTimeParseException e ) {
                    System.out.println("Неверно введена дата");
                }

                System.out.println("Введите id фильма:");
                Integer movieId = Integer.valueOf(scanner.nextLine()) ;
                Movie movie = movieService.getMovieById(movieId)
                        .orElseThrow(() -> new RuntimeException(String.format("Фильма " +
                                "с id= %s не найдено ", movieId)));

                if (movieId != null && MovieDateTime != null) {
                    eventService.updateEvent(event.getEventId(), movieId, MovieDateTime);
                }
                //Вызов операции покупки билета
            } else if (step.equals("11")) {
                System.out.println("Удаление сеанса");
                System.out.println("Введите id сеанса для удаления:");
                Integer id = Integer.valueOf(scanner.nextLine()) ;

                Event event= eventService.getEventById(id)
                        .orElseThrow(() -> new RuntimeException(String.format("Сеанса " +
                                "с id= %s не найдено ", id)));

                eventService.deleteEvent(event.getEventId());
                //Вызов операции удаления товара
            } else if (step.equals("12")) {
                System.out.println("Просмотр сеанса");
                eventService.getAllEvents().forEach(System.out::println);
                //Вызов операции удаления товара
            } else if (step.equals("13")) {
                System.out.println("Добавление билета");
                System.out.println("Введите код события:");
                Integer eventId = Integer.valueOf(scanner.nextLine());
                System.out.println("Введите номер места:");
                String place = scanner.nextLine();
                System.out.println("Введите цену за билет:");
                Double price = Double.valueOf(scanner.nextLine());

                Ticket ticket = new Ticket(null, eventId, price, place, false);
                ticketService.createTicket(ticket);

            } else if (step.equals("14")) {
                System.out.println("Изменение билета");
                System.out.println("Введите id билета для изменения:");
                Integer id = Integer.valueOf(scanner.nextLine()) ;

                Ticket ticket = ticketService.getTicketById(id)
                        .orElseThrow(() -> new RuntimeException(String.format("Билета " +
                                "с id= %s не найдено ", id)));

                System.out.println("Введите id события:");
                Integer eventId = Integer.valueOf(scanner.nextLine());
                System.out.println("Введите номер места:");
                String place = scanner.nextLine();
                System.out.println("Введите цену билета:");
                Double price = Double.valueOf(scanner.nextLine());
                if (eventId != null && place != null && price != null) {
                   ticketService.updateTicket(ticket.getTicketId(), eventId, place, price);
                }
            } else if (step.equals("15")) {
                System.out.println("Удаление билета");
                System.out.println("Введите id билета для удаления:");
                Integer id = Integer.valueOf(scanner.nextLine()) ;

                Ticket ticket = ticketService.getTicketById(id)
                        .orElseThrow(() -> new RuntimeException(String.format("Билета " +
                                "с id= %s не найдено ", id)));

                ticketService.deleteTicket(ticket.getTicketId());
            } else if (step.equals("16")) {
                System.out.println("Просмотр билетов");
                ticketService.getAllTickets().forEach(System.out::println);
            }

            else if (step.equals("0")) {
                GlobalController.start();
                break;
            }

        }
    }

    private static void addEvent(MovieService movieService, EventService eventService) {
        System.out.println("Введите время сеанса:");
        Integer DD;
        do {
            System.out.println("Число:");
            DD = Integer.valueOf(scanner.nextLine()) ;
        } while (DD<1 || DD>31);
        Integer MM;
        do {
            System.out.println("Месяц:");
            MM = Integer.valueOf(scanner.nextLine());
        } while (MM<1 || MM>12);
        Integer YYYY;
        do {
            System.out.println("Год:");
            YYYY = Integer.valueOf(scanner.nextLine()) ;
        } while (YYYY<2023 || YYYY>3000);
        Integer HH24;
        do {
            System.out.println("Время (часы) от 0 до 23 ");
            HH24 = Integer.valueOf(scanner.nextLine()) ;
        } while (HH24<0 || HH24>23);
        Integer MI;
        do {
            System.out.println("Время (минуты) от 0 до 59 ");
            MI = Integer.valueOf(scanner.nextLine()) ;
        } while (MI<0 || MI>59);
        LocalDateTime MovieDateTime = null;
        try{
             MovieDateTime = LocalDateTime.of(YYYY, MM, DD, HH24, MI);
        } catch ( DateTimeParseException e ) {
            System.out.println("Неверно введена дата");
        }

        System.out.println("Введите id фильма:");
        Integer movieId = Integer.valueOf(scanner.nextLine()) ;
        Movie movie = movieService.getMovieById(movieId)
                .orElseThrow(() -> new RuntimeException(String.format("Фильма " +
                        "с id= %s не найдено ", movieId)));

        Event event = new Event(movieId, MovieDateTime);
        eventService.createEvent(event);
    }

    private static void deleteUser(UserService userService) {
        System.out.println("Введите id пользователя для удаления:");
        Integer id = scanner.nextInt();
        User user = userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Пользователя " +
                        "с id= %s не найдено ", id)));
        userService.deleteByAdmin(user.getId());
    }

    private static void updateUser(UserService userService) {
        System.out.println("Введите id пользователя для изменения:");
        Integer id = Integer.valueOf(scanner.nextLine()) ;
        User user = userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Пользователя " +
                        "с id= %s не найдено ", id)));

        System.out.println("Введите логин:");
        String username = scanner.nextLine();
        System.out.println("Введите пароль:");
        String password = scanner.nextLine();
        System.out.println("Введите email:");
        String email = scanner.nextLine();
        System.out.println("Введите роль:");
        String role = scanner.nextLine();

        if (username != null && password != null && email != null && role != null) {
            userService.updateByAdmin(user.getId(), username, password, email, UserRole.valueOf(role));
        }
        ;
    }

    private static void addUser(UserService userService) {
        System.out.println("Введите логин:");
        String username = scanner.nextLine();
        System.out.println("Введите пароль:");
        String password = scanner.nextLine();
        System.out.println("Введите email:");
        String email = scanner.nextLine();
        System.out.println("Введите роль:");
        String role = scanner.nextLine();
        User user = new User(username, password, email, role);
        userService.createByAdmin(user);
    }
}
