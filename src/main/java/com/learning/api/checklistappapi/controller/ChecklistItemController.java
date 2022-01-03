package com.learning.api.checklistappapi.controller;

import com.learning.api.checklistappapi.dto.ChecklistItemItemDTO;
import com.learning.api.checklistappapi.dto.NewResourceDTO;
import com.learning.api.checklistappapi.dto.UpdateStatusDTO;
import com.learning.api.checklistappapi.entity.ChecklistItemEntity;
import com.learning.api.checklistappapi.service.ChecklistItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/v1/api/checklist")
public class ChecklistItemController {

    private ChecklistItemService checklistItemService;

    public ChecklistItemController(ChecklistItemService checklistItemService) {
        this.checklistItemService = checklistItemService;
    }


    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ChecklistItemItemDTO> getAllChecklistItems() {
        List resp = StreamSupport.stream(this.checklistItemService.findAllCheklistItems().spliterator(), false)
                .map(checklistItemEntity -> ChecklistItemItemDTO.toDTO(checklistItemEntity))
                .collect(Collectors.toList());

        return new ResponseEntity(resp, HttpStatus.OK);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NewResourceDTO> createNewChecklistItem(@RequestBody ChecklistItemItemDTO checklistItemItemDTO) throws ValidationException {
        if (checklistItemItemDTO.getCategory().getGuid() == null) {
            throw new ValidationException("Categoria é nula");
        }
        ChecklistItemEntity resp = this.checklistItemService.addNewChecklistItem(
                checklistItemItemDTO.getDescription(),
                checklistItemItemDTO.getCategory().getGuid(),
                checklistItemItemDTO.getIsCompleted(),
                checklistItemItemDTO.getDeadline()
                );


        return new ResponseEntity<>(new NewResourceDTO(resp.getGuid()), HttpStatus.CREATED);

    }

    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateChecklistItems(@RequestBody ChecklistItemItemDTO checklistItemItemDTO) throws ValidationException {
        if (!StringUtils.hasLength(checklistItemItemDTO.getGuid())) {
            throw new ValidationException("Checklist item obrigatório");
        }
        this.checklistItemService.updateChecklistItem(checklistItemItemDTO.getGuid(), checklistItemItemDTO.getDescription(),
                checklistItemItemDTO.getIsCompleted(), checklistItemItemDTO.getDeadline(),
                checklistItemItemDTO.getCategory().getGuid() != null ? checklistItemItemDTO.getCategory().getGuid() : null);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "{guid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteChecklist(@PathVariable String guid) {
        this.checklistItemService.deleteChecllistItems(guid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(value = "{guid}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdateStatusDTO> updateStatusCompleted(@PathVariable String guid, @RequestBody UpdateStatusDTO statusDTO) {
            this.checklistItemService.updateIsCompleteStatus(guid, statusDTO.isCompleted);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}

