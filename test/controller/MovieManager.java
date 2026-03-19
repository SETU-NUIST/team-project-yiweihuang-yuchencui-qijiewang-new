package controller;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import models.Movie;

import javax.swing.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class MovieManager {
    private ArrayList<Movie> addMovie = new ArrayList<>();
    public void saveToXML() {
        XStream xstream = new XStream(new DomDriver());
        xstream.allowTypes(new Class[]{Movie.class});
        try (FileWriter writer = new FileWriter("movies.xml")) {
            xstream.toXML(addMovie, writer);
            JOptionPane.showMessageDialog(
                    null,
                    "数据已成功保存到 movies.xml",
                    "保存成功",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "保存失败：" + e.getMessage(),
                    "错误",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void loadFromXML() {
        XStream xstream = new XStream(new DomDriver());
        xstream.allowTypes(new Class[]{Movie.class, ArrayList.class});
        try (FileReader reader = new FileReader("movies.xml")) {
            ArrayList<Movie> loadedMovies = (ArrayList<Movie>) xstream.fromXML(reader);
            addMovie.clear();
            addMovie.addAll(loadedMovies);
            JOptionPane.showMessageDialog(
                    null,
                    "数据已成功从 movies.xml 加载",
                    "加载成功",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "加载失败：" + e.getMessage(),
                    "错误",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    public MovieManager() {
        // 构造函数不再需要初始化 Scanner
    }

    /**
     * 初始化预设电影数据
     */
    public void initializeSimpleMovies() {
        addMovie.add(new Movie("The Shawshank Redemption", "Frank Darabont", 1994, 9.7, "Drama/Crime", "Hope can set you free"));
        addMovie.add(new Movie("Farewell My Concubine", "Chen Kaige", 1993, 9.6, "Drama/Romance", "A timeless classic"));
        addMovie.add(new Movie("Leon: The Professional", "Luc Besson", 1994, 9.4, "Drama/Crime", "The story of a hitman"));
        addMovie.add(new Movie("Interstellar", "Christopher Nolan", 2014, 9.4, "Sci-fi/Adventure", "Love transcends time and space"));
        addMovie.add(new Movie("Spirited Away", "Hayao Miyazaki", 2001, 9.4, "Animation/Fantasy", "Best of Miyazaki"));
    }

    /**
     * 主菜单界面
     */
    public void showMainMenu() {
        while (true) {
            // 1. 定义菜单选项（顺序和 case 严格对应）
            String[] options = {
                    "1. View all movies",
                    "2. Add new movie",
                    "3. Search movies",
                    "4. Delete movie",
                    "5. Modify movie",
                    "6. Show data by ranking level",
                    "7. Rating list",
                    "8. Save to XML",
                    "9. Load from XML",
                    "0. Log out"
            };

            // 2. 弹出选择对话框
            int choice = JOptionPane.showOptionDialog(
                    null,
                    "Please select an operation:",
                    "Douban models.Movie Manager",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            // 3. 优先处理菜单功能（switch 放在前面）
            switch (choice) {
                case 0 -> displayAllMovies();
                case 1 -> addNewMovie();
                case 2 -> searchMovie();
                case 3 -> deleteMovie();
                case 4 -> updateMovie();
                case 5 -> showDataByRankingLevel();
                case 6 -> showRatingRanking();
                case 7 -> saveToXML();      // 绑定保存XML
                case 8 -> loadFromXML();   // 绑定加载XML
                case 9 -> {                // 手动选择 Log out 时退出
                    JOptionPane.showMessageDialog(null, "Logged out successfully!");
                    return; // 退出整个菜单循环
                }
                case -1 -> { // 点击窗口关闭按钮时退出
                    JOptionPane.showMessageDialog(null, "Logged out successfully!");
                    return;
                }
            }
        }
    }


    // 1. 查看所有电影
    public void displayAllMovies() {
        if (addMovie.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No movie data available!", "Info", JOptionPane.WARNING_MESSAGE);
            return;
        }

        StringBuilder sb = new StringBuilder("📋 models.Movie List:\n");
        for (int i = 0; i < addMovie.size(); i++) {
            sb.append(String.format("%d. 《%s》\n", i + 1, addMovie.get(i).getTitle()));
        }
        sb.append("\nEnter number to view details (0 to return):");

        String input = JOptionPane.showInputDialog(null, sb.toString());
        try {
            int index = Integer.parseInt(input);
            if (index > 0 && index <= addMovie.size()) {
                Movie selected = addMovie.get(index - 1);
                String detail = String.format(
                        "🎬 models.Movie Details\n" + "=".repeat(20) +
                                "\nTitle: %s\nDirector: %s\nYear: %d\nRating: %.1f/10\nGenre: %s\nReview: %s",
                        selected.getTitle(), selected.getDirector(), selected.getYear(),
                        selected.getRating(), selected.getGenre(), selected.getReview()
                );
                JOptionPane.showMessageDialog(null, detail);
            }
        } catch (NumberFormatException ignored) {}
    }

    // 2. 添加新电影
    private void addNewMovie() {
        try {
            String title = JOptionPane.showInputDialog("Enter models.Movie Title:");
            if (title == null) return;
            String director = JOptionPane.showInputDialog("Enter Director:");
            int year = Integer.parseInt(JOptionPane.showInputDialog("Enter Year (e.g. 2024):"));
            double rating = Double.parseDouble(JOptionPane.showInputDialog("Enter Rating (0.0-10.0):"));
            String genre = JOptionPane.showInputDialog("Enter Genre:");
            String review = JOptionPane.showInputDialog("Enter Review:");

            addMovie.add(new Movie(title, director, year, rating, genre, review));
            JOptionPane.showMessageDialog(null, "Added successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid input! Operation cancelled.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 3. 搜索电影
    private void searchMovie() {
        String[] criteria = {"Title", "Director", "Genre", "Rating(>=)", "Year"};
        int type = JOptionPane.showOptionDialog(null, "Search by:", "Search",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, criteria, criteria[0]);

        if (type == -1) return;

        String keyword = JOptionPane.showInputDialog("Enter keyword:");
        if (keyword == null || keyword.isEmpty()) return;

        StringBuilder results = new StringBuilder("🔍 Search Results:\n");
        boolean found = false;

        for (Movie m : addMovie) {
            boolean match = switch (type) {
                case 0 -> m.getTitle().toLowerCase().contains(keyword.toLowerCase());
                case 1 -> m.getDirector().toLowerCase().contains(keyword.toLowerCase());
                case 2 -> m.getGenre().toLowerCase().contains(keyword.toLowerCase());
                case 3 -> m.getRating() >= Double.parseDouble(keyword);
                case 4 -> m.getYear() == Integer.parseInt(keyword);
                default -> false;
            };

            if (match) {
                results.append(m.toString()).append("\n");
                found = true;
            }
        }

        JOptionPane.showMessageDialog(null, found ? results.toString() : "No relevant movies found.");
    }

    // 4. 删除电影
    private void deleteMovie() {
        if (addMovie.isEmpty()) return;

        String input = JOptionPane.showInputDialog("Enter the sequence number to delete:");
        try {
            int index = Integer.parseInt(input);
            if (index > 0 && index <= addMovie.size()) {
                Movie m = addMovie.get(index - 1);
                int confirm = JOptionPane.showConfirmDialog(null,
                        "Are you sure to delete 《" + m.getTitle() + "》?", "Confirm", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    addMovie.remove(index - 1);
                    JOptionPane.showMessageDialog(null, "Deleted successfully!");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid selection.");
        }
    }

    // 5. 修改电影信息
    private void updateMovie() {
        if (addMovie.isEmpty()) return;
        String input = JOptionPane.showInputDialog("Enter sequence number to modify:");
        try {
            int index = Integer.parseInt(input);
            if (index > 0 && index <= addMovie.size()) {
                Movie m = addMovie.get(index - 1);

                String newTitle = JOptionPane.showInputDialog("New Title [" + m.getTitle() + "]:", m.getTitle());
                if (newTitle != null) m.setTitle(newTitle);

                String newRating = JOptionPane.showInputDialog("New Rating [" + m.getRating() + "]:", m.getRating());
                if (newRating != null) m.setRating(Double.parseDouble(newRating));

                JOptionPane.showMessageDialog(null, "Updated successfully!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Update failed.");
        }
    }

    // 6. 按评分等级显示
    private void showDataByRankingLevel() {
        String[] levels = {"1. Masterpiece(>=9.0)", "2. Excellent(8.0-8.9)", "3. Good(7.0-7.9)", "4. Average(6.0-6.9)", "5. Poor(<6.0)"};
        String choice = (String) JOptionPane.showInputDialog(null, "Select Level:", "Ranking Level",
                JOptionPane.QUESTION_MESSAGE, null, levels, levels[0]);

        if (choice == null) return;
        int levelNum = Character.getNumericValue(choice.charAt(0));

        StringBuilder sb = new StringBuilder("Level " + levelNum + " Movies:\n");
        boolean found = false;
        for (Movie m : addMovie) {
            if (m.getRatingLevel(m.getRating()) == levelNum) {
                sb.append(m.toString()).append("\n");
                found = true;
            }
        }
        JOptionPane.showMessageDialog(null, found ? sb.toString() : "No movies in this level.");
    }

    // 7. 评分排行榜
    private void showRatingRanking() {
        if (addMovie.isEmpty()) return;

        ArrayList<Movie> sortedList = new ArrayList<>(addMovie);
        // 冒泡排序逻辑
        for (int i = 0; i < sortedList.size() - 1; i++) {
            for (int j = 0; j < sortedList.size() - 1 - i; j++) {
                if (sortedList.get(j).getRating() < sortedList.get(j + 1).getRating()) {
                    Movie temp = sortedList.get(j);
                    sortedList.set(j, sortedList.get(j + 1));
                    sortedList.set(j + 1, temp);
                }
            }
        }

        StringBuilder sb = new StringBuilder("🏆 models.Movie Rating Ranking:\n");
        for (int i = 0; i < sortedList.size(); i++) {
            Movie m = sortedList.get(i);
            sb.append(String.format("Rank %d: %.1f - %s\n", i + 1, m.getRating(), m.getTitle()));
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }
}