package org.verapdf.gui;

import org.apache.log4j.Logger;
import org.verapdf.gui.tools.GUIConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * About Panel
 *
 * @author Maksim Bezrukov
 */
class AboutPanel extends JPanel {

	/**
	 * ID for serialisation
	 */
	private static final long serialVersionUID = -4011118192914036216L;

	private static final Logger LOGGER = Logger
			.getLogger(AboutPanel.class);

	private JButton okButton;
	JDialog dialog;

	AboutPanel() throws IOException {
		setLayout(new BorderLayout());


		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		add(mainPanel, BorderLayout.CENTER);

		LogoPanel logo = new LogoPanel(GUIConstants.LOGO_NAME, GUIConstants.LOGO_BACKGROUND, GUIConstants.LOGOPANEL_BORDER_WIDTH);

		mainPanel.add(logo);

		PartnersPanel partners = new PartnersPanel(GUIConstants.PARTNERS_NAME, GUIConstants.PARTNERS_BACKGROUND);

		mainPanel.add(partners);

		okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				dialog.setVisible(false);
			}
		});


		JButton urlLabel = new JButton(GUIConstants.LOGO_LINK_TEXT);
		urlLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				try {
					Desktop.getDesktop().browse(new URI(GUIConstants.LOGO_LINK_URL));
				} catch (IOException | URISyntaxException excep) {
					JOptionPane.showMessageDialog(AboutPanel.this, GUIConstants.ERROR, GUIConstants.ERROR, JOptionPane.ERROR_MESSAGE);
					LOGGER.error("Exception in opening link " + GUIConstants.LOGO_LINK_URL, excep);
				}

			}
		});

		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 2));

		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		labelPanel.add(urlLabel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(okButton);

		bottomPanel.add(labelPanel);
		bottomPanel.add(buttonPanel);
		add(bottomPanel, BorderLayout.SOUTH);
	}

	void showDialog(Component parent, String title) {

		Frame owner;
		if (parent instanceof Frame) {
			owner = (Frame) parent;
		} else {
			owner = (Frame) SwingUtilities.getAncestorOfClass(Frame.class, parent);
		}

		if (dialog == null || dialog.getOwner() != owner) {
			dialog = new JDialog(owner, true);
			dialog.setResizable(false);
			dialog.add(this);
			dialog.getRootPane().setDefaultButton(okButton);
			dialog.pack();
			dialog.setTitle(title);
		}

		dialog.setLocation(GUIConstants.ABOUTDIALOG_COORD_X, GUIConstants.ABOUTDIALOG_COORD_Y);
		dialog.setVisible(true);
	}

}
