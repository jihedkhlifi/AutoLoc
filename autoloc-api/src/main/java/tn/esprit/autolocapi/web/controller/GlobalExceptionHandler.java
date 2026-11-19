package tn.esprit.autolocapi.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import tn.esprit.autolocapi.exception.ConflitException;
import tn.esprit.autolocapi.exception.ResourceNotFoundException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {
        return construire(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> erreurs = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e -> erreurs.put(e.getField(), e.getDefaultMessage()));
        ex.getBindingResult().getGlobalErrors().forEach(e -> erreurs.put(e.getObjectName(), e.getDefaultMessage()));
        Map<String, Object> body = corps(HttpStatus.BAD_REQUEST, "Validation des donnees echouee");
        body.put("erreurs", erreurs);
        return ResponseEntity.badRequest().body(body);
    }

    /** Contraintes (@NotBlank...) posees directement sur les @RequestParam / @PathVariable. */
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<Map<String, Object>> handleValidationParametres(HandlerMethodValidationException ex) {
        List<String> erreurs = ex.getAllErrors().stream()
                .map(erreur -> erreur.getDefaultMessage())
                .toList();
        Map<String, Object> body = corps(HttpStatus.BAD_REQUEST, "Validation des parametres echouee");
        body.put("erreurs", erreurs);
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, Object>> handleParametreManquant(MissingServletRequestParameterException ex) {
        return construire(HttpStatus.BAD_REQUEST, "Parametre obligatoire manquant : " + ex.getParameterName());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleTypeInvalide(MethodArgumentTypeMismatchException ex) {
        return construire(HttpStatus.BAD_REQUEST, "Valeur invalide pour le parametre '" + ex.getName()
                + "' : " + ex.getValue() + " (dates au format yyyy-MM-dd, identifiants numeriques)");
    }

    @ExceptionHandler(ConflitException.class)
    public ResponseEntity<Map<String, Object>> handleConflit(ConflitException ex) {
        return construire(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(IllegalArgumentException ex) {
        return construire(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleJsonInvalide(HttpMessageNotReadableException ex) {
        return construire(HttpStatus.BAD_REQUEST, "Corps JSON invalide ou valeur non reconnue");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleContrainte(DataIntegrityViolationException ex) {
        return construire(HttpStatus.CONFLICT, "Operation impossible : contrainte de donnees violee");
    }

    private ResponseEntity<Map<String, Object>> construire(HttpStatus statut, String message) {
        return ResponseEntity.status(statut).body(corps(statut, message));
    }

    private Map<String, Object> corps(HttpStatus statut, String message) {
        Map<String, Object> corps = new LinkedHashMap<>();
        corps.put("statut", statut.value());
        corps.put("message", message);
        corps.put("horodatage", LocalDateTime.now().withNano(0).toString());
        return corps;
    }
}
