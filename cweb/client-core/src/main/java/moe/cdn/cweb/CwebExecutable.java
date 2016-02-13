package moe.cdn.cweb;

import com.google.inject.Guice;
import com.google.inject.Injector;

import moe.cdn.cweb.security.CwebSignatureValidationService;

public class CwebExecutable {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new CwebModule());
        CwebApi cwebApi = injector.getInstance(CwebApi.class);
        
        
    }

}
