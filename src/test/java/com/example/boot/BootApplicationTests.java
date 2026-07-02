package com.example.boot;

import com.example.boot.dto.BoardDTO;
import com.example.boot.service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BootApplicationTests {

	@Autowired
	private BoardService boardService;

	@Test
	void contextLoads() {
		for(int i = 0; i < 500; i++){
			BoardDTO boardDTO = BoardDTO.builder()
					.title("test tile " + (int)(Math.random()*1000))
					.writer("tester no. " + (int)(Math.random()*5000+400)+"@testing.com")
					.content("Test Content"+ i + "\n" +i + i + i*99 + i + i + i )
					.build();
			boardService.insert(boardDTO);
		}
	}

}
