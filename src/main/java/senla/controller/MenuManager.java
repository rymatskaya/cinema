package senla.controller;

import senla.model.Event;
import senla.model.Movie;
import senla.repository.*;
import senla.service.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import static senla.controller.GlobalController.user;

public class MenuManager {
    private static Scanner scanner = new Scanner(System.in);

    public static void MenuManager() {
        EventRepository eventRepository = new EventRepositoryImpl();
        EventService eventService = new EventServiceImpl(eventRepository);
        TicketRepository ticketRepository = new TicketRepositoryImpl();
        TicketService ticketService = new TicketServiceImpl(ticketRepository);
        UserRepository userRepository = new UserRepositoryImpl();
        UserService userService = new UserServiceImpl(userRepository);
        MovieRepository movieRepository = new MovieRepositoryImpl();
        MovieService movieService = new MovieServiceImpl(movieRepository);

        while (true) {
            String str = """
                     1 - Просмотр мероприятий
                     2 - Купить билет
                     3 - Вернуть билет
                     4 - Просмотреть свободные билеты
                     5 - Редактировать мероприятие (сеанс)
                     6 - Редактировать название фильма
                     0 - Вернуться в главное меню                   
                                        
                    """;
            System.out.println(str);
            System.out.print("Выберите вариант меню: ");
            String step = scanner.nextLine();

            if (step.equals("1")) {
                System.out.println("Просмотр мероприятий");
                eventService.getAllEventsFull().forEach(System.out::println);
            } else if (step.equals("2")) {
                System.out.println("Покупка билета");
                System.out.println("Выберите мероприятие и введите его номер");
                eventService.getAllEventsFull().forEach(System.out::println);
                Integer eventId = Integer.valueOf(scanner.nextLine()) ;
                System.out.println("Введите место ");
                String place = scanner.nextLine();
                if (eventId != null && place != null  ) {
                    ticketService.buyTicket(eventId, place, user.getId());
                }
            } else if (step.equals("3")) {
                System.out.println("Возврат билета");
                ticketService.getUserTickets(user.getId()).forEach(System.out::println);
                System.out.println("Выберите мероприятие и введите его номер");
                eventService.getAllEventsFull().forEach(System.out::println);
                Integer eventId = Integer.valueOf(scanner.nextLine()) ;
                System.out.println("Введите место ");
                String place = scanner.nextLine();
                if (eventId != null && place != null  ) {
                    ticketService.returnTicket(eventId, place, user.getId());
                }
            } else if (step.equals("4")) {
                System.out.println("Просмотр купленных билетов: ");
                ticketService.getUserTickets(user.getId()).forEach(System.out::println);
            } else if (step.equals("5")) {
                System.out.println("Редактировать мероприятие (сеанс): ");
                eventService.getAllEventsFull().forEach(System.out::println);
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
                movieService.getAllMovies().forEach(System.out::println);
                System.out.println("Введите id фильма:");
                Integer movieId = Integer.valueOf(scanner.nextLine()) ;
                Movie movie = movieService.getMovieById(movieId)
                        .orElseThrow(() -> new RuntimeException(String.format("Фильма " +
                                "с id= %s не найдено ", movieId)));

                if (movieId != null && MovieDateTime != null) {
                    eventService.updateEvent(event.getEventId(), movieId, MovieDateTime);
                }

            } else if (step.equals("6")) {
                System.out.println("Редактировать название фильма: ");

                System.out.println("Просмотр фильмов");
                movieService.getAllMovies().forEach(System.out::println);
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


            } else if (step.equals("0")) {
                GlobalController.start();
                break;
                //Вызов операции удаления товара
            }

        }
    }
}
