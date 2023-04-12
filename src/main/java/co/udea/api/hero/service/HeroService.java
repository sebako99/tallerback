package co.udea.api.hero.service;

import co.udea.api.hero.exception.BusinessException;
import co.udea.api.hero.model.Hero;
import co.udea.api.hero.repository.HeroRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HeroService {

    private final Logger log = LoggerFactory.getLogger(HeroService.class);

    private HeroRepository heroRepository;

    public HeroService(HeroRepository heroRepository){
        this.heroRepository = heroRepository;
    }
    public Hero getHero(Integer id){
        Optional<Hero> optionalHero = heroRepository.findById(id);
        if(!optionalHero.isPresent()){
            log.info("No se encuentra un heroe con ID: "+id);
            throw new BusinessException("El heroe no existe");
        }
        return optionalHero.get();
    }
    
    public Hero crearHero(Hero hero){
        Optional<Hero> optionalHero = heroRepository.findById(hero.getId());
        if(optionalHero.isPresent()){
            log.info("Ya existe un heroe con ID: "+hero.getId());
            throw new BusinessException("Ya existe un heroe con el ID "+hero.getId());
        }
        return heroRepository.save(hero);
    }
    public Hero actualizarHero(Integer id, Hero hero){
        Optional<Hero> optionalHero = heroRepository.findById(id);
        if(!optionalHero.isPresent()){
            log.info("No se encuentra un heroe con ID: "+id);
            throw new BusinessException("El heroe no existe");
        }
        Hero existingHero = optionalHero.get();
        existingHero.setName(hero.getName());
        return heroRepository.save(existingHero);
    }
    
    public List<Hero> getAllHeroes() {
        return heroRepository.findAll();
    }
    
    public void deleteHero(Integer id){
        Optional<Hero> optionalHero = heroRepository.findById(id);
        if(!optionalHero.isPresent()){
            log.info("No se encuentra un heroe con ID: "+id);
            throw new BusinessException("El heroe no existe");
        }
        heroRepository.deleteById(id);
    }

    
}
