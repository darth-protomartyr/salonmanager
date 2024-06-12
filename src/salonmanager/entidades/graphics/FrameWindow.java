package salonmanager.entidades.graphics;

public class FrameWindow extends FrameGeneral {

    public FrameWindow() {
        setVisible(true);
        setBounds(anchoUnit, altoUnit, anchoUnit * 30, altoUnit * 43);
        setResizable(false);
        frame = this;
    }
}
