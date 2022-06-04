package pro.sky.java.shelterbot;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum CommandNames {
    GREET_COMMAND("/start"),
    INFO_COMMAND("/info"),
    HOW_WORK_COMMAND("/how_the_shelter_works"),
    SAFETY_COMMAND("/safety_precautions"),
    GIVE_CONTACTS_COMMAND("/give_contacts"),
    CALL_EMPLOYEE_COMMAND("/call_an_employee"),

    SECRET_COMMAND("/secret");

    @Getter
    private final String commandName;

    public static CommandNames valueOfCommandName(String name) {
        for (CommandNames value : values()) {
            if (value.commandName.equals(name)) {
                return value;
            }
        }
        return CommandNames.CALL_EMPLOYEE_COMMAND;
    }

}
