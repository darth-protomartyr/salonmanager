package salonmanager.entidades.graphics;

public class FrameWindow extends FrameGeneral {

    public FrameWindow() {
        setVisible(true);
        setBounds(5, 0, 390, 300);
        setResizable(false);
        frame = this;
    }
}
