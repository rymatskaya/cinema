package senla.controller.helper;

public class Constants {
    public static final String MAIN_MENU = """
            Добро пожаловать!
            1 - регистрация
            2 - вход
            3 - просмотр мероприятий
            0 - выйти
            """;
    public static final String CHOISE = "Выберите пункт меню: ";
    public static final String REGISTRATION_MENU = "Меню регистрации";
    public static final String INPUT_LOGIN = "Введите логин:";
    public static final String INPUT_PASSWORD = "Введите пароль:";
    public static final String INPUT_EMAIL = "Введите email:";
    public static final String INPUT_ROLE = "Введите роль:";
    public static final String INPUT_MENU = "Меню входа";
    public static final String VIEW_EVENTS = "Просмотр мероприятий";
    public static final String EXIT = "Выход";
    public static final String USER_MENU = """
             1 - Просмотр мероприятий
             2 - Купить билет
             3 - Вернуть билет
             4 - Просмотреть купленные билеты
             0 - Вернуться в главное меню                   
            """;
    public static final String BUY_TICKET = "Покупка билета";
    public static final String CHOISE_EVENT = "Выберите мероприятие и введите его номер";
    public static final String CHOISE_PLACE = "Введите место ";
    public static final String RETURN_TICKET = "Возврат билета";
    public static final String VIEW_TICKETS = "Просмотр купленных билетов: ";
    public static final String MANAGER_MENU = """
             1 - Просмотр мероприятий
             2 - Купить билет
             3 - Вернуть билет
             4 - Просмотреть проданные билеты
             5 - Редактировать мероприятие (сеанс)
             6 - Редактировать название фильма
             0 - Вернуться в главное меню                   
            """;
    public static final String ADD_EVENT = "Добавление сеанса";
    public static final String EDIT_EVENT_ADMIN = "Изменение сеанса";
    public static final String INPUT_IDEVENT = "Введите код события:";
    public static final String EDIT_EVENT = "Редактировать мероприятие (сеанс): ";
    public static final String DELETE_EVENT = "Удаление сеанса";
    public static final String INPUT_EVENT_FOR_EDIT = "Введите id сеанса для изменения:";
    public static final String INPUT_TIME = "Введите время сеанса:";
    public static final String DD = "Число:";
    public static final String MM = "Месяц:";
    public static final String YYYY = "Год:";
    public static final String HH24 = "Время (часы) от 0 до 23 ";
    public static final String MI = "Время (минуты) от 0 до 59 ";
    public static final String WRONG_DATE = "Неверно введена дата";
    public static final String INPUT_IDMOVIE = "Введите id фильма:";
    public static final String EDIT_MOVIE_NAME = "Редактировать название фильма: ";
    public static final String VIEW_MOVIES = "Просмотр фильмов";
    public static final String INPUT_MOVIE_NAME = "Введите название фильма:";
    public static final String ADMIN_MENU = """
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
    public static final String ADD_USER = "Добавление пользователя";
    public static final String EDIT_USER = "Изменение пользователя";
    public static final String DELETE_USER = "Удаление пользователя";
    public static final String VIEW_USERS = "Просмотр пользователей";

    public static final String ADD_MOVIE = "Добавление фильма";
    public static final String EDIT_MOVIE = "Изменение фильма";
    public static final String DELETE_MOVIE = "Удаление фильма";

    public static final String ADD_TICKET = "Добавление билета";
    public static final String INPUT_PLACE = "Введите номер места:";
    public static final String INPUT_PRICE = "Введите цену за билет:";
    public static final String EDIT_TICKET = "Изменение билета";
    public static final String INPUT_IDTICKET = "Введите id билета";
    public static final String DELETE_TICKET = "Удаление билета";
    public static final String INPUT_IDUSER = "Введите id пользователя:";
}
