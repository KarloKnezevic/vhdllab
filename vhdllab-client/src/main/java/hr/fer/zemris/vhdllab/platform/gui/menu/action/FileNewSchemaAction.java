package hr.fer.zemris.vhdllab.platform.gui.menu.action;

import hr.fer.zemris.vhdllab.entities.FileType;
import hr.fer.zemris.vhdllab.platform.gui.menu.AbstractMenuAction;
import hr.fer.zemris.vhdllab.platform.manager.editor.WizardManager;

import java.awt.event.ActionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileNewSchemaAction extends AbstractMenuAction {

    private static final long serialVersionUID = 1L;

    @Autowired
    protected WizardManager systemContainer;

    @Override
    public void actionPerformed(ActionEvent e) {
        systemContainer.createNewFileInstance(FileType.SCHEMA);
    }

}
