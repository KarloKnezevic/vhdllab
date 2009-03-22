package hr.fer.zemris.vhdllab.platform.manager.simulation;

import hr.fer.zemris.vhdllab.entity.File;
import hr.fer.zemris.vhdllab.platform.listener.EventPublisher;

public interface SimulationManager extends EventPublisher<SimulationListener> {

    void simulateWithDialog();

    void simulateLast();

    void simulate(File file);

}
