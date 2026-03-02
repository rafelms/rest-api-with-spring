package br.com.rafelms.rest_with_spring.mapper;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import java.util.ArrayList;
import java.util.List;

public class ObjectMapper {
    private static Mapper mapper = DozerBeanMapperBuilder.buildDefault(); // Fara as conversões automáticas de origin para destination no DTO

    public static <O, D> D parseObject(O origin, Class<D> destination){
        return mapper.map(origin, destination);
    }

    public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination){

        List<D> destinationObjects = new ArrayList<D>(); // Montando a lista de objetos de destino
        for(O o : origin){
            destinationObjects.add(mapper.map(o, destination));
        }

        return destinationObjects;
    }


}
