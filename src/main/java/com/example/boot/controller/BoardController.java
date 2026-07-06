package com.example.boot.controller;

//BoardController
import com.example.boot.dto.BoardDTO;
import com.example.boot.handler.PagingHandler;
import com.example.boot.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/board/*")
@Slf4j
public class BoardController {
    private final BoardService boardService;

//    @GetMapping
//    public String register(){
//        /*/board/register   보드컨트롤러에서 register 라는 getmapping으로 연결.
//        * 들어오는 경로랑 나가는 경로가 같을경우 생략이 가능해짐.
//        * */
//        return "/board/register";
//    }

     @GetMapping("/register")
    public void register(){}

    @PostMapping("/register")
    public String register(BoardDTO boardDTO){
         log.info(">>>>>> boardDTO {}", boardDTO);
         Long bno = boardService.insert(boardDTO);

         return "redirect:/board/list";
    }

//    @GetMapping("/list")
//    public void list(Model model){
//         List<BoardDTO> list = boardService.getList();
//         model.addAttribute("list", list);
//    }
//paging이 없는 리스트.
    @GetMapping("/list")
    public void list(Model model,
                     @RequestParam(name = "pageNo", required = false, defaultValue = "1") int pageNo){
         //required = false의 존재 = 필수는 아니다. 없을수도 있어.
        Page<BoardDTO> list = boardService.getList(pageNo);
        model.addAttribute("list", list);


        PagingHandler ph = new PagingHandler(list, pageNo);
        log.info("ph >>> {}", ph);

        model.addAttribute("ph", ph);

    }


    @GetMapping("/detail")
    public void detail(@RequestParam("bno") Long bno, Model model){
         BoardDTO boardDTO = boardService.getDetail(bno);
         model.addAttribute("board", boardDTO);
    }


    @PostMapping("/update")
    public String update(BoardDTO boardDTO, RedirectAttributes redirectAttributes){
         //redirect 시 해당 위치로 객체를 보내주는 역할을 하는 애가 존재함.
        //redirect attribute
        boardService.update(boardDTO);
        redirectAttributes.addAttribute("bno", boardDTO.getBno());
        return "redirect:/board/detail";
    }

    @GetMapping("/delete")
    public String remove(@RequestParam("bno")Long bno){
        boardService.delete(bno);
         return "redirect:/board/list";

    }



}
