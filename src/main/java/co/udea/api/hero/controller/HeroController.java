package co.udea.api.hero.controller;

import co.udea.api.hero.model.Hero;
import co.udea.api.hero.service.HeroService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/heroes")
public class HeroController {

    private final Logger log = LoggerFactory.getLogger(HeroController.class);

    private HeroService heroService;

    public HeroController(HeroService heroService){
        this.heroService = heroService;
    }

    @GetMapping("{id}")
    @ApiOperation(value = "Busca un hero por su id",  response = Hero.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Hero encontrado existosamente"),
            @ApiResponse(code = 400, message = "La petición es invalida"),
            @ApiResponse(code = 500, message = "Error interno al procesar la respuesta")})
    public ResponseEntity<Hero> getHero(@PathVariable Integer id){
        log.info("Rest request buscar heroe por id: "+ id);
        return ResponseEntity.ok(heroService.getHero(id));
    }
    @PostMapping
    @ApiOperation(value = "Crea un nuevo heroe", response = Hero.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Heroe creado correctamente"),
            @ApiResponse(code = 400, message = "La solicitud es invalida"),
            @ApiResponse(code = 500, message = "Error interno al procesar la respuesta")})
    public ResponseEntity<Hero> createHero(@RequestBody Hero hero){
        log.info("Rest request crear heroe: "+ hero);
        Hero createdHero = heroService.crearHero(hero);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHero);
    }
    @PutMapping("{id}")
    @ApiOperation(value = "Actualiza un heroe existente", response = Hero.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Heroe actualizado correctamente"),
            @ApiResponse(code = 400, message = "La solicitud es invalida"),
            @ApiResponse(code = 500, message = "Error interno al procesar la respuesta")})
    public ResponseEntity<Hero> updateHero(@PathVariable Integer id, @RequestBody Hero hero){
        log.info("Rest request actualizar heroe con id "+ id +": "+ hero);
        Hero updatedHero = heroService.actualizarHero(id, hero);
        return ResponseEntity.ok(updatedHero);
    }
    @DeleteMapping("{id}")
    @ApiOperation(value = "Elimina un heroe existente", response = Void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Hero eliminado existosamente"),
            @ApiResponse(code = 400, message = "La petición es invalida"),
            @ApiResponse(code = 500, message = "Error interno al procesar la respuesta")})
    public ResponseEntity<Void> deleteHero(@PathVariable Integer id){
        log.info("Rest request eliminar heroe con id "+ id);
        heroService.deleteHero(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @ApiOperation(value = "Busca todos los heroes",  response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Heroes encontrados existosamente"),
            @ApiResponse(code = 500, message = "Error interno al procesar la respuesta")})
    public ResponseEntity<List<Hero>> getAllHeroes() {
        log.info("Rest request buscar todos los heroes");
        return ResponseEntity.ok(heroService.getAllHeroes());
    }
}
