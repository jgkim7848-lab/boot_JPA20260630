package com.example.boot.controller;
//CommentController
import com.example.boot.dto.CommentDTO;
import com.example.boot.repository.CommentRepository;
import com.example.boot.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.util.List;

@Slf4j
@RequestMapping("/comment")
@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommentRepository commentRepository;

    @PostMapping(value = "/post",
        consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public ResponseEntity<String> post(@RequestBody CommentDTO commentDTO){
        long cno = commentService.post(commentDTO);


        return cno > 0 ? new ResponseEntity<String>("1", HttpStatus.OK):
                new ResponseEntity<String>("0", HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @GetMapping(value = "/list/{bno}",
//    produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<CommentDTO>> list(@PathVariable("bno") long bno){
//        List<CommentDTO> list = commentService.getList(bno);
//        log.info("list>>>>{}", list);
//
//        return new ResponseEntity<List<CommentDTO>>(list, HttpStatus.OK);
//    }  페이징이 없는 리스트

    @GetMapping(value = "/list/{bno}/{page}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<CommentDTO>> list(@PathVariable("bno") long bno,
                                                 @PathVariable("page") int page){
        Page<CommentDTO> list = commentService.getList(bno, page);
        log.info("list>>>>{}", list);

        return new ResponseEntity<Page<CommentDTO>>(list, HttpStatus.OK);
    }

    @DeleteMapping(value = "/remove/{cno}")
    public ResponseEntity<String> remove(@PathVariable("cno") long cno){

        commentService.remove(cno);


        return cno > 0 ? new ResponseEntity<String>("1", HttpStatus.OK):
                new ResponseEntity<String>("0", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(value="/modify", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> modify(@RequestBody CommentDTO commentDTO){
        long cno = commentService.modify(commentDTO);


        return cno > 0 ? new ResponseEntity<String>("1", HttpStatus.OK):
                new ResponseEntity<String>("0", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
