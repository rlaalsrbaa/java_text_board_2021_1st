package com.kmg.exam.board;

import java.util.*;

public class Main {
  static void makeTestData(List<Article> articles) {
    for (int i = 1; i <= 15; i++) {
      articles.add(new Article(i, "제목" + i, "내용" + i));
    }
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    System.out.println("== 게시판 v 0.1 ==");
    System.out.println("== 프로그램 시작 ==");

    int articlesLastId = 0;
    List<Article> articles = new ArrayList<>();

    makeTestData(articles);

    if (articles.size() > 0) {
      articlesLastId = articles.get(articles.size() - 1).id;
    }

    while (true) {
      System.out.printf("명령) ");
      String cmd = sc.nextLine();
      Rq rq = new Rq(cmd);
      Map<String, String> params = rq.getParams();

      if (rq.getActionMethodName().equals("exit")) {
        break;
      } else if (rq.getActionMethodName().equals("list")) {
        System.out.println("- 게시물 리스트 -");
        System.out.println("--------------------");
        System.out.println("번호 / 제목");
        System.out.println("--------------------");

        if(params.containsKey("searchKeyword") && params.containsKey("page") && params.containsKey("orderBy")){
          List<Article> filteredArticles1 = new ArrayList<>();
          List<Article> filteredArticles2 = new ArrayList<>();
          int page;
          boolean orderByIdDesc = false;
          String searchKeyword = params.get("searchKeyword");
          for(Article article : articles){
            if(article.title.contains(searchKeyword) || article.body.contains(searchKeyword)){
              filteredArticles1.add(article);
            }
          }
          page = Integer.parseInt(params.get("page"));
          for(int i = filteredArticles1.size() - (page*10);  i < filteredArticles1.size()-((page-1)*10);i++ ){
            try{
              filteredArticles2.add(filteredArticles1.get(i));
            }catch (IndexOutOfBoundsException e){}
          }
          if(params.get("orderBy").equals("idDesc")){
            orderByIdDesc = true;
          }else if(params.get("orderBy").equals("idAsc")){
            orderByIdDesc = false;
          }
          if(orderByIdDesc){
             for(int i = filteredArticles2.size()-1; i >= 0;i--){
               System.out.printf("%d / %s\n",filteredArticles2.get(i).id,filteredArticles2.get(i).title);
             }
          }else{
            for(Article article : filteredArticles2){
              System.out.printf("%d / %s\n",article.id,article.title);
            }
          }
          continue;
        }


         if (params.isEmpty()) {
            for (int i = articles.size() - 1; i >= 0; i--) {
              System.out.printf("%d / %s\n", articles.get(i).id, articles.get(i).title);
            }
          }
      }                                                                     // ========== list 종료 ============
      else if (rq.getActionMethodName().equals("detail")) {

        try {
          articles.get(Integer.parseInt(params.get("id")) - 1);
        } catch (Exception e) {
          System.out.println("id값은 양의 정수를 입력해주세요.");
          continue;
        }

        if (Integer.parseInt(params.get("id")) > articles.size()) {
          System.out.println("게시물이 존재하지 않습니다.");
          continue;
        }


        Article article = articles.get(Integer.parseInt(params.get("id")) - 1);
        System.out.println("- 게시물 상세내용 -");
        System.out.printf("번호 : %d\n", article.id);
        System.out.printf("제목 : %s\n", article.title);
        System.out.printf("내용 : %s\n", article.body);
      } else if (rq.getActionMethodName().equals("write")) {
        System.out.println("- 게시물 등록 -");
        System.out.printf("제목 : ");
        String title = sc.nextLine();
        System.out.printf("내용 : ");
        String body = sc.nextLine();
        int id = articlesLastId + 1;
        articlesLastId = id;

        Article article = new Article(id, title, body);
        articles.add(article);
        System.out.println("생성된 게시물 객체 : " + article);

        System.out.printf("%d번 게시물이 입력되었습니다.\n", article.id);
      } else {
        System.out.printf("입력된 명령어 : %s\n", cmd);
      }
    }

    System.out.println("== 프로그램 종료 ==");

    sc.close();
  }
}

class Article {
  int id;
  String title;
  String body;

  Article(int id, String title, String body) {
    this.id = id;
    this.title = title;
    this.body = body;
  }

  @Override
  public String toString() {
    return String.format("{id: %d, title: \"%s\", body: \"%s\"}", id, title, body);
  }
}

class Rq {
  private String url;
  private String urlPath;
  private Map<String, String> params;
  private String controllerTypeCode;
  private String controllerName;
  private String actionMethodName;

  Rq(String url) {
    this.url = url;
    urlPath = Util.getUrlPathFromUrl(this.url);
    params = Util.getParamsFromUrl(this.url);
    controllerTypeCode = Util.getControllerTypeCodeFromUrl(this.url);
    controllerName = Util.getControllerName(this.url);
    actionMethodName = Util.getActionMethodName(this.url);
  }

  public Map<String, String> getParams() {
    return params;
  }

  public String getUrlPath() {
    return urlPath;
  }

  public String getControllerTypeCode() {
    return controllerTypeCode;
  }

  public String getControllerName() {
    return controllerName;
  }

  public String getActionMethodName() {
    return actionMethodName;
  }
}

class Util {
  static Map<String, String> getParamsFromUrl(String url) {

    Map<String, String> params = new HashMap<>();
    String[] urlBits = url.split("\\?", 2);

    if (urlBits.length == 1) {
      return params;
    }

    String queryStr = urlBits[1];
    for (String bit : queryStr.split("&")) {
      String[] bits = bit.split("=", 2);
      if (bits.length == 1) {
        continue;
      }
      params.put(bits[0], bits[1]);
    }

    return params;
  }

  static String getUrlPathFromUrl(String url) {
    return url.split("\\?", 2)[0];
  }

  public static String getControllerTypeCodeFromUrl(String url) {
    return url.split("\\?")[0].split("/")[1];
  }

  public static String getControllerName(String url) {
    return url.split("\\?")[0].split("/")[2];
  }

  public static String getActionMethodName(String url) {
    return url.split("\\?")[0].split("/")[3];
  }
}