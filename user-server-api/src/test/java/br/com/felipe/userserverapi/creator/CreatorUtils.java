package br.com.felipe.userserverapi.creator;


import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;


public class CreatorUtils {

    private static final PodamFactory podamFactory = new PodamFactoryImpl();

    public static <T> T generatedMock(final Class<T> clazz) {
        return podamFactory.manufacturePojo(clazz);
    }



}
