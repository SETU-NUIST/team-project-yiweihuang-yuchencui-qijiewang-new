package main;

import controller.MovieManager;

import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        JOptionPane.showMessageDialog(
                null,
                "Welcome to Douban models.Movie Management System",
                "Welcome",
                JOptionPane.INFORMATION_MESSAGE
        );

        String username = JOptionPane.showInputDialog(
                null,
                "Please enter your username:",
                "Login",
                JOptionPane.QUESTION_MESSAGE
        );

        String password = JOptionPane.showInputDialog(
                null,
                "Please enter your password:",
                "Login",
                JOptionPane.QUESTION_MESSAGE
        );

        String correctUsername = "Qijie,Wang";
        String correctPassword = "123456";

        if (username != null && username.equals(correctUsername)) {
            if (password != null && password.equals(correctPassword)) {
                JOptionPane.showMessageDialog(
                        null,
                        "Login successful! Welcome to use the system ~",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Wrong password, please try again",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Wrong account, try please again",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }


        MovieManager manager = new MovieManager();
        manager.initializeSimpleMovies();
        manager.showMainMenu();
    }



}
