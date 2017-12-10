package jp.co.ack.crossword.application;

import org.springframework.stereotype.Service;

@Service
public class GameService {







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
