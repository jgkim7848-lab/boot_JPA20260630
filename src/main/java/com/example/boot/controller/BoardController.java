package com.example.boot.controller;

import com.example.boot.dto.BoardDTO;
import com.example.boot.dto.BoardFileDTO;
import com.example.boot.dto.FileDTO;
import com.example.boot.handler.FileHandler;
import com.example.boot.handler.FileRemoveHandler;
import com.example.boot.handler.PagingHandler;
import com.example.boot.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/board/*")
@Controller
public class BoardController {
    private final BoardService boardService;
    private final FileHandler fileHandler;

/*    @GetMapping("/register")
    public String register(){
        *//* /board/register  => (board 컨트롤러에서 register라는 getmapping으로 연결) *//*
        // 들어오는 경로와 나가는 경로가 같을 경우 생략가능
        return "/board/register";
    }*/

    @GetMapping("/register")
    public void register(){}

    @PostMapping("/register")
    public String register(BoardDTO boardDTO,
                           @RequestParam(name = "files", required = false)MultipartFile[] files){
        // 추가된 파일 처리
        // DB로 갈 fileDto 객체 만들기
        // 실제 저장
        List<FileDTO> fileList = null;
        if(files != null && files[0].getSize() > 0){
            // 파일이 존재한다면...  핸들러를 호출
            fileList = fileHandler.uploadFile(files);
        }
        //log.info(">> boardDTO >> {}", boardDTO);
        //log.info(">> fileList >> {}", fileList);
        Long bno = boardService.insert(new BoardFileDTO(boardDTO, fileList));

        //Long bno = boardService.insert(boardDTO);

        return "redirect:/board/list";
    }

//    @GetMapping("/list")
//    public void list(Model model){
//        // 페이징 없는 리스트
//        List<BoardDTO> list = boardService.getList();
//        model.addAttribute("list", list);
//    }

    @GetMapping("/list")
    public void list(Model model,
                     @RequestParam(name = "pageNo", required = false,
                             defaultValue = "1") int pageNo,
                     @RequestParam(name = "type", required = false) String type,
                     @RequestParam(name = "keyword", required = false) String keyword){
        log.info("type >> {}", type);
        log.info("keyword >> {}", keyword);
        // page + search
        Page<BoardDTO> list = boardService.getList(pageNo, type, keyword);
//        model.addAttribute("list",list);
//        log.info("getTotalElements >> {}", list.getTotalElements()); // 전체 게시글 수
//        log.info("getTotalPages >> {}", list.getTotalPages()); // realEndPage
//        log.info("list >> {}", list.hasPrevious()); // 이전 버튼의 필요 여부
//        log.info("list >> {}", list.hasNext()); // 다음 버튼의 필요 여부

        PagingHandler ph = new PagingHandler(list, pageNo, type, keyword);
        log.info("ph>>{}",ph);
        model.addAttribute("ph",ph);
    }

    @GetMapping("/detail")
    public void detail(@RequestParam("bno") Long bno, Model model){
        BoardFileDTO boardFileDTO = boardService.getDetail(bno);
        model.addAttribute("boardFile", boardFileDTO);
    }

    @PostMapping("/update")
    public String update(BoardDTO boardDTO,
                         RedirectAttributes redirectAttributes,
                         @RequestParam(name = "files", required = false)MultipartFile[] files){
        List<FileDTO> fileDTOList = null;
        if(files != null && files[0].getSize() > 0){
            fileDTOList = fileHandler.uploadFile(files);
        }
        boardService.modify(new BoardFileDTO(boardDTO, fileDTOList));
        //boardService.update(boardDTO);

        // redirect시 해당 위치로 객체를 보내주는 역할
        redirectAttributes.addAttribute("bno",boardDTO.getBno());
        return "redirect:/board/detail";
    }

    @GetMapping("/remove")
    public String remove(@RequestParam("bno")Long bno){
        boardService.remove(bno);
        return "redirect:/board/list";
    }

    // header나 다른 데이터의 상태를 많이 보내지 않을 때 간편하게 사용
    @ResponseBody
    @DeleteMapping("/file/{uuid}")
    public String fileRemove(@PathVariable("uuid")String uuid){
        // responseBody만 보내기
        long bno = boardService.fileRemove(uuid);
        return bno > 0 ? "1" : "0";
    }

//    @DeleteMapping("/file/{uuid}")
//    public ResponseEntity<String> fileRemove(@PathVariable("uuid")String uuid){
//        // 비동기
//        log.info("uuid >>> {}", uuid);
//        // 폴더에 있는 파일을 먼저 삭제 후 DB의 데이터를 삭제
//        FileDTO removeFile = boardService.getFile(uuid);
//        FileRemoveHandler fileRemoveHandler = new FileRemoveHandler();
//        boolean isDel = fileRemoveHandler.removeFile(removeFile);
//        long bno = 0;
//        if(isDel){
//            // db 삭제요청
//            bno = boardService.fileRemove(uuid);
//        }
//        return bno > 0 ? new ResponseEntity<String>("1", HttpStatus.OK) :
//            new ResponseEntity<String>("0", HttpStatus.INTERNAL_SERVER_ERROR);
//    }

}




//package com.example.boot.controller;
//
////BoardController
//import com.example.boot.dto.BoardDTO;
//import com.example.boot.dto.BoardFileDTO;
//import com.example.boot.dto.FileDTO;
//import com.example.boot.handler.FileHandler;
//import com.example.boot.handler.PagingHandler;
//import com.example.boot.service.BoardService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.domain.Page;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.util.List;
//
//@RequiredArgsConstructor
//@Controller
//@RequestMapping("/board/*")
//@Slf4j
//public class BoardController {
//    private final BoardService boardService;
//    private final FileHandler fileHandler;
//
////    @GetMapping
////    public String register(){
////        /*/board/register   보드컨트롤러에서 register 라는 getmapping으로 연결.
////        * 들어오는 경로랑 나가는 경로가 같을경우 생략이 가능해짐.
////        * */
////        return "/board/register";
////    }
//
//     @GetMapping("/register")
//    public void register(){}
//
//    @PostMapping("/register")
//    public String register(BoardDTO boardDTO, @RequestParam(name = "files", required = false) MultipartFile[] files){
//         List<FileDTO> fileList = null;
//         if(files != null && files[0].getSize() > 0){
//             fileList = fileHandler.uploadFile(files);
//         }
//         log.info(">>>>>> fileList {}", fileList);
//         log.info(">>>>>> boardDTO {}", boardDTO);
//         //Long bno = boardService.insert(boardDTO);
//        Long bno = boardService.insert(new BoardFileDTO(boardDTO, fileList));
//
//         return "redirect:/board/list";
//    }
//
////    @GetMapping("/list")
////    public void list(Model model){
////         List<BoardDTO> list = boardService.getList();
////         model.addAttribute("list", list);
////    }
////paging이 없는 리스트.
//    @GetMapping("/list")
//    public void list(Model model,
//                     @RequestParam(name = "pageNo", required = false, defaultValue = "1") int pageNo){
//         //required = false의 존재 = 필수는 아니다. 없을수도 있어.
//        Page<BoardDTO> list = boardService.getList(pageNo);
//        model.addAttribute("list", list);
//
//
//        PagingHandler ph = new PagingHandler(list, pageNo);
//        log.info("ph >>> {}", ph);
//
//        model.addAttribute("ph", ph);
//
//    }
//
//
//    @GetMapping("/detail")
//    public void detail(@RequestParam("bno") Long bno, Model model){
//         BoardFileDTO boardFileDTO = boardService.getDetail(bno);
//         model.addAttribute("boardFile", boardFileDTO);
//    }
//
//
//    @PostMapping("/update")
//    public String update(BoardDTO boardDTO, RedirectAttributes redirectAttributes){
//         //redirect 시 해당 위치로 객체를 보내주는 역할을 하는 애가 존재함.
//        //redirect attribute
//        boardService.update(boardDTO);
//        redirectAttributes.addAttribute("bno", boardDTO.getBno());
//        return "redirect:/board/detail";
//    }
//
//    @GetMapping("/delete")
//    public String remove(@RequestParam("bno")Long bno){
//        boardService.delete(bno);
//         return "redirect:/board/list";
//
//    }
//
//
//
//}
