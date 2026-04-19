$().ready(function () {
    
    $(".page-navigator").find("a").on("click", function () {
        var pageNo = $(this).data("page-no");
        var listSize = $("#list-size").val();
        var searchType = $("#search-type").val();
        var searchKeyword = $("#search-keyword").val();

        location.href = "/?pageNo=" + pageNo 
                        + "&listSize=" + listSize 
                        + "&searchType=" + searchType 
                        + "&searchKeyword=" + searchKeyword;
    });
    
    $("#list-size").on("change", function () {
        //location.href = "/?pageNo=0&listSize=" + $(this).val();
        $(".search-button").trigger("click");
    });
    
    $(".search-button").on("click", function () {
        // /?pageNo=0&listSize= #list-size값&searchType=#search-type값&
        //   searchKeyWord=#search-keyword값
        var pageNo = 0;
        var listSize = $("#list-size").val();
        var searchType = $("#search-type").val();
        var searchKeyword = $("#search-keyword").val();
        
        location.href = "/?pageNo=" + pageNo 
                        + "&listSize=" + listSize 
                        + "&searchType=" + searchType 
                        + "&searchKeyword=" + searchKeyword;
    });
    
  // ".add-file"을 클릭하면
  // 새로운 파일 인풋과 버튼을
  // ".attach-files" 아래에 추가한다.
  //   $(".add-file").on("click", function () {
  $(".attach-files").on("click", ".add-file", function () {
    //새로운 파일이 추가될 때 마다 기존의  "add-file"을 "del-file"로 변경하고
    //텍스트는 "+"에서 "-"로 변경한다.

    $(this)
      .closest(".attach-files")
      .children(".add-file")
      .removeClass("add-file")
      .addClass("del-file")
      .text("-")
      .off("click") //할당되어있던 이벤트를 제거한다.
      .on("click", function () {
        //버튼 왼쪽에 있는 인풋 태그 삭제
        $(this).prev().remove();
        //버튼도 삭제.
        $(this).remove();
      });

    var fileInput = $("<input />");
    fileInput.attr({
      type: "file",
      name: "attachFile",
    });

    var addButton = $("<button />");
    addButton.attr("type", "button").addClass("add-file").text("+");
    $(".attach-files").append(fileInput).append(addButton);
  });
});
