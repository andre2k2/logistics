package br.com.ecoder.logistics.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.ecoder.logistics.model.Map;
import br.com.ecoder.logistics.model.Point;
import br.com.ecoder.logistics.model.Route;

public class MapValidation implements ConstraintValidator<MapConstraint, Map> {

    @Override
    public void initialize(MapConstraint annotation) {
    }

    @Override
    public boolean isValid(Map map, ConstraintValidatorContext context) {

        return validateMap(map, context);
    }

    private boolean validateMap(Map map, ConstraintValidatorContext context) {

        boolean valid = true;

        if (map == null) {
            context.buildConstraintViolationWithTemplate("O mapa está nulo. O corpo da requisição deve ser preenchido.").addConstraintViolation();
            valid &= false;
        }

        if (map.getName() == null || map.getName().trim().isEmpty()) {
            context.buildConstraintViolationWithTemplate("O nome do mapa está nulo.").addConstraintViolation();
            valid &= false;
        }

        if (map.getRoutes() == null || map.getRoutes().isEmpty()) {
            context.buildConstraintViolationWithTemplate("Não há nenhuma rota no mapa.").addConstraintViolation();
            valid &= false;
        }

        if (map.getRoutes() != null) {

            for (Route route : map.getRoutes()) {
                valid &= validateRoute(route, context);
            }
        }

        return valid;
    }

    private boolean validateRoute(Route route, ConstraintValidatorContext context) {

        boolean valid = true;

        if (route == null) {
            context.buildConstraintViolationWithTemplate("A rota está nula.").addConstraintViolation();
            valid &= false;
        }

        if (route.getOrigin() == null) {
            context.buildConstraintViolationWithTemplate("A origem da rota não foi definida.").addConstraintViolation();
            valid &= false;
        }

        if (route.getDestiny() == null) {
            context.buildConstraintViolationWithTemplate("O destino da rota não foi definido.").addConstraintViolation();
            valid &= false;
        }

        valid &= validatePoint(route.getOrigin(), context);

        valid &= validatePoint(route.getDestiny(), context);

        return valid;
    }

    private boolean validatePoint(Point point, ConstraintValidatorContext context) {

        boolean valid = true;

        if (point == null) {
            context.buildConstraintViolationWithTemplate("O ponto está nulo.").addConstraintViolation();
            valid &= false;
        }

        if (point.getName() == null || point.getName().trim().isEmpty()) {
            context.buildConstraintViolationWithTemplate("O nome do ponto está nulo.").addConstraintViolation();
            valid &= false;
        }

        return valid;
    }

}
