package com.kmg.exam.board;

import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    System.out.println("== 게시판 v 0.1 ==");
    System.out.println("== 프로그램 시작 ==");
    int articleLastId = 0;
    Article article = new Article();
    while (true) {
      System.out.printf("명령)");
      String cmd = sc.nextLine();

      if (cmd.equals("exit")) {
        System.out.println("== 프로그램 종료 ==");
        break;
      } else if (cmd.equals("/usr/article/write")) {
        System.out.println("- 게시물 등록 -");

        System.out.printf("제목 : ");
        article.title = sc.nextLine();

        System.out.printf("내용 : ");
        article.body = sc.nextLine();

        article.id = articleLastId + 1;
        articleLastId = article.id;


        System.out.println("생성된 게시물 객체 : " + article);
        System.out.printf("%d번 게시물이 등록되었습니다.\n", article.id);
        continue;
      } else if (cmd.equals("/usr/article/detail")) {
        article.articleDetail();
        continue;
      }
    }

    sc.close();
  }
}

class Article {
  int id;
  String title;
  String body;

  public void articleDetail() {
    System.out.println("- 게시물 상세보기 -");
    System.out.printf("번호 : %d\n제목 : %s\n내용 : %s\n", this.id, this.title, this.body);
  }

  public String toString() {

    return String.format("{id: \"%d\", title: \"%s\", body: \"%s\"}", id, title, body);


  }
}
