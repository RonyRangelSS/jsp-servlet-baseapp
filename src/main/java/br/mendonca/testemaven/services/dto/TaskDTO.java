package br.mendonca.testemaven.services.dto;

import br.mendonca.testemaven.model.entities.Task;

public class TaskDTO {

    private String uuid;
    private String userId;
    private String taskName;
    private Boolean isCompleted;
    private Integer priority;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public static TaskDTO taskMapper(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setUuid(task.getUuid());
        dto.setTaskName(task.getTaskName());
        dto.setPriority(task.getPriority());
        dto.setCompleted(task.getCompleted());
        dto.setUserId(task.getUserId());

        return dto;
    }
}
