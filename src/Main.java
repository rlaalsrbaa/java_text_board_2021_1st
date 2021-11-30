import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
  public static void articleDetail(List<Article> arr){

    System.out.println("- 게시물 상세내용 -");
    System.out.printf("번호 : %d\n", arr.get(arr.size()-1).id);
    System.out.printf("제목 : %s\n", arr.get(arr.size()-1).title);
    System.out.printf("내용 : %s\n", arr.get(arr.size()-1).body);
  }
  public static void makeTestData(List<Article> arr){
    arr.add(new Article(1,"제목1","내용1"));
    arr.add(new Article(2,"제목2","내용2"));
    arr.add(new Article(3,"제목3","내용3"));
  }
  public static void showArticleList(List<Article> arr){
    System.out.println("- 게시물 리스트 -");
    System.out.println("--------------------");
    System.out.println("번호 / 제목");
    System.out.println("--------------------");
    for (int i = arr.size() - 1; i >= 0; i--) {
      System.out.printf("%d / %s\n", arr.get(i).id, arr.get(i).title);
    }
  }
  public static void articleWrite(Scanner sc, int lastArticleId, List<Article> arr){
    System.out.println("- 게시물 등록 -");
    System.out.printf("제목 : ");
    String title = sc.nextLine();
    System.out.printf("내용 : ");
    String body = sc.nextLine();
    int id = lastArticleId + 1;
    lastArticleId = id;

    arr.add(new Article(id,title,body));
    System.out.println("생성된 게시물 객체 : " + arr.get(arr.size()-1));
    System.out.printf("%d번 게시물이 입력되었습니다.\n", id);
  }
  public static void main(String[] args) {
    List<Article> arr = new ArrayList<>();
    makeTestData(arr);
    int lastArticleId = arr.size();
    Scanner sc = new Scanner(System.in);
    System.out.println("== 게시판 v 0.1 ==");
    System.out.println("== 프로그램 시작 ==");

    while (true) {                              // ------while문 시작-----
      System.out.printf("명령) ");
      String cmd = sc.nextLine();
      if (cmd.equals("/usr/article/write")) {   //   ------ write 시작 -----
        articleWrite(sc,lastArticleId,arr);
      }                                        //   ------ write 끝 -----
      else if (cmd.equals("/usr/article/detail")) {     // ------detail 시작-------
        if (arr.isEmpty()) {
          System.out.println("게시물이 존재하지 않습니다.");
          continue;
        }
        articleDetail(arr);
      }                                            // ------detail 끝-------
      else if (cmd.equals("/usr/article/list")) {    // --------list 시작------
        if (arr.isEmpty()) {
          System.out.println("게시물이 존재하지 않습니다.");
          continue;
        }
        showArticleList(arr);
      }                                         // --------list 끝------
      else if (cmd.equals("exit")) {             //   ------ exit 시작------
        System.out.println("== 프로그램 종료 ==");
        break;
      }                                        //   ------ exit 끝------
    }                                          //   ------ while문 끝 -----
  }
}

class Article {
  int id;
  String title;
  String body;


  public Article(int id, String title, String body) {
    this.id = id;
    this.title = title;
    this.body = body;
  }

  @Override
  public String toString() {
    return "{" +
        "id=" + id +
        ", title='" + title + '\'' +
        ", body='" + body + '\'' +
        '}';
  }
}