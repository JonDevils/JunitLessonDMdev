package com.example.junitlessondmdev.user;

import com.example.junitlessondmdev.dto.User;

import com.example.junitlessondmdev.paramersolver.UserServiceParamResolver;
import lombok.NoArgsConstructor;
import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;
import org.assertj.core.api.MatcherAssert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@Tag("fast")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)//порядок запуска методов
//methodname - по порядку алфавит. displayname - и аннотация @Displayname("") - меняет название
@TestInstance(TestInstance.Lifecycle.PER_CLASS)//Per_class - создаем один класс а не множество как с Per_metod (т.е статик можно не делать)
@ExtendWith({
        UserServiceParamResolver.class
})
public class UserServiceTest { //public только в этом случае

    private static UserService userService;


    private static final User IVAN =  User.of(1L,"Ivan","123");
    private static final User PETR =  User.of(2L,"Petr","111");
    UserServiceTest(TestInfo testInfo) {
        System.out.println( );
    }
    @BeforeAll
    void init () {
        System.out.println("BeforeAll: "+ this );
    } // не статик т.к per_class

    @BeforeEach
    void prepare(UserService userService) {
        System.out.println("Before each: " + this);
        this.userService = userService;
    }

    @Test
    @Order(1)//вместе с MethodOrderer.OrderAnnotation
    public void userEmptyIfNoUserAdded(UserService userService) {
        System.out.println("test 1 : " + this);
    var users =  userService.getAll();
        System.out.println(userService.getAll());
    assertTrue(users.isEmpty(),()-> "Не нулевое значение");
    }




    @Test
    @DisplayName("usSizeIfAdd")
    void usersSizeIfUserAdedd() {
        System.out.println("test 2 : " + this);
        var UserSer = new UserService();
        UserSer.add(IVAN);
        UserSer.add(PETR);

    var users = UserSer.getAll();
    assertThat(users).hasSize(2);
//    assertEquals(2,users.size());
    }
@Test
void TestFunc
//    @Test
@RepeatedTest(value = 2,name = RepeatedTest.LONG_DISPLAY_NAME)
    void LoginSuccessIfUserExists(RepetitionInfo repetitionInfo) {
        UserService us = new UserService();
        us.add(IVAN);
        Optional<User> mbuser =  us.login(IVAN.getUsername(),IVAN.getPassword());
        assertThat(mbuser).isPresent();
        mbuser.ifPresent(user -> assertThat(user).isEqualTo(IVAN));
//        assertTrue(mbuser.isPresent());
//        mbuser.ifPresent(user -> assertEquals(IVAN,user));
    }
    @Test
    void usersConvertedToMapId() {
        UserService us = new UserService();
        us.add(IVAN,PETR);
        Map<Long,User>users =us.getAllConvertedById();
        assertAll(
                ()->assertThat(users).containsKeys(IVAN.getId(),PETR.getId()),
                ()->assertThat(users).containsValues(IVAN,PETR)
                );

    }






    //8.8


    @AfterEach
    void deleteDataFromDatabase() {
        System.out.println("AfterEach : " + this);
    }

    @AfterAll
     void afterDatabase() {
        System.out.println("AfterAll : "+ this );
    }//this можно  т.к не статик
    @Nested
    @Tag("Login")
    class LoginTest  {
        @Test
        @Tag("login")//это можно убрать
        void throwExpIfUsernameOrPasswordIsNull() {
//        try {
            var us  = new UserService();
            assertAll(
                    ()-> {
                        var ex = assertThrows(IllegalArgumentException.class, () -> us.login(null, "22gg"));
                        assertThat(ex.getMessage()).isEqualTo("username or password / null");
                    },
                    ()->assertThrows(IllegalArgumentException.class,()->us.login("duuv",null))
            );
//            assertThrows(IllegalArgumentException.class,()->us.login(null,"22gg"));

//            fail("login should trow ex on null");
//        } catch (IllegalArgumentException exception) {
//            assertTrue(true);
//        }

        }

        @Test
        @Tag("login")
        void LogicFailIfUserDoesNotExist() {
            var userSer = new UserService();
            userSer.add(IVAN);
            var maybeUser = userSer.login("dummy",IVAN.getPassword());
            assertTrue(maybeUser.isEmpty());

        }

        @Test
        @Tag("login")
        void logicFailIfPasswordIsNotCorrect() {
            var userSer = new UserService();
            userSer.add(IVAN,PETR);
            var maybeuser = userSer.login(IVAN.getUsername(),"14ff");
            assertTrue(maybeuser.isEmpty());
        }


    }
    @ParameterizedTest()
//    @ArgumentsSource()
//    @NullSource//поставляет null в наш параметр ! но только для одного парметра
//    @EmptySource
//    @NullAndEmptySource
//    @ValueSource (strings = {"Ivan","Petr"})
    @MethodSource("getArgumentsForLoginTest")
    void loginParametrizedTest(String username,String password,Optional<User>user) {
        userService.add(IVAN,PETR);

      var myb =  userService.login(username,password);
      assertThat(myb).isEqualTo(user);
    }
    static Stream<Arguments> getArgumentsForLoginTest() {
        return Stream.of(
               Arguments.of( "Ivan","123",Optional.of(IVAN)),
                Arguments.of( "Petr","111",Optional.of(PETR)),
                Arguments.of("Petr","der",Optional.empty()),
                Arguments.of( "der","111",Optional.empty())
        );
    }

}