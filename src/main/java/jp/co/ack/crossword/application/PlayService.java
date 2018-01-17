package jp.co.ack.crossword.application;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class PlayService {

	public void create(){
		int num = 5;
		int size = num * num;
		String[] template  = new String[size];

		//黒埋め
		for(int i = 0; i < size;i++){
			Random rnd = new Random();
			int ran = rnd.nextInt(3) + 1;
			if(ran == 1){
				template[i] = "■";
			}else{
				template[i] = "□";
			}
		}

		//四隅と分断を除去


		for(int i = 0; i < size;i++){
			System.out.print(template[i]);
			int res = (i+1)%num;
			if( res == 0){
				System.out.println("");
			}
		}
		System.out.println("");
	}
/*	@Autowired
	TemplateRepository templateRepository;

	*//**
	 * 受け取ったTemplateオブジェクトのcreatedに現在日時、userに"test_user"を設定してDBに保存する。
	 *
	 * @param template
	 * @return
	 *//*
	public void create(Template template) {
		Mst user = new Mst();
		user.setId(SecurityContextHolder.getContext().getAuthentication().getName());
		template.setUser(user);
		template.setCreated(new Date());
		template.setUpdated(new Date());

		for (TemplateItem item : template.getTemplateItems()) {
			item.setTemplate(template);
		}
		template = templateRepository.save(template);
	}

	public List<Template> findByOrderByCreated() {
		return templateRepository.findByOrderByCreatedDesc();
	}

	public Template findByName(String name) {
		return templateRepository.findByName(name);
	}

	public void delete(List<Template> templates) {
		templateRepository.delete(templates);
	}*/
}
