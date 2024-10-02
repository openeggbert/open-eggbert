package com.openeggbert.lwjgl3;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class Networking implements ApplicationListener {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Skin skin;
    private Stage stage;
    private Label labelDetails;
    private Label labelMessage;
    private TextButton button;
    private TextArea textIPAddress;
    private TextArea textMessage;
    
    public final static float VIRTUAL_SCREEN_HEIGHT = 400;
    public final static float VIRTUAL_SCREEN_WIDTH = 300;
    
    private Viewport viewport;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        batch = new SpriteBatch();
        
        // Load UI skin from file
        skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        stage = new Stage();
        
        // Set input processor for the stage
        Gdx.input.setInputProcessor(stage);

        // Get IPv4 addresses
        List<String> addresses = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            for (NetworkInterface ni : Collections.list(interfaces)) {
                for (InetAddress address : Collections.list(ni.getInetAddresses())) {
                    if (address instanceof Inet4Address) {
                        addresses.add(address.getHostAddress());
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        // Create IP address string
        String ipAddress = String.join("\n", addresses);

        // Setup UI
        VerticalGroup vg = new VerticalGroup().space(3).pad(5).fill();
        vg.setBounds(0, 0, VIRTUAL_SCREEN_WIDTH, VIRTUAL_SCREEN_HEIGHT);
        
        labelDetails = new Label(ipAddress, skin);
        labelMessage = new Label("Hello world", skin);
        button = new TextButton("Send message", skin);
        textIPAddress = new TextArea("", skin);
        textMessage = new TextArea("", skin);

        vg.addActor(labelDetails);
        vg.addActor(labelMessage);
        vg.addActor(textIPAddress);
        vg.addActor(textMessage);
        vg.addActor(button);
        
        stage.addActor(vg);
        
        // Create and set up FitViewport
        viewport = new FitViewport(VIRTUAL_SCREEN_WIDTH, VIRTUAL_SCREEN_HEIGHT, camera);
        stage.setViewport(viewport);
        
        // Set camera position
        camera.position.set(VIRTUAL_SCREEN_WIDTH / 2, VIRTUAL_SCREEN_HEIGHT / 2, 0);
        camera.update();

        final int PORT = 22223;
        // New thread for listening to incoming socket connections
        new Thread(() -> {
            ServerSocketHints serverSocketHint = new ServerSocketHints();
            serverSocketHint.acceptTimeout = 0;

            ServerSocket serverSocket = Gdx.net.newServerSocket(Protocol.TCP, PORT, serverSocketHint);
            
            while (true) {
                Socket socket = serverSocket.accept(null);
                BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
                
                try {
                    labelMessage.setText(buffer.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        
        // Listener for button
        button.addListener(new ClickListener() {
            @Override 
            public void clicked(InputEvent event, float x, float y) {
                String textToSend = textMessage.getText().isEmpty() ? 
                    "Doesn't say much but likes clicking buttons\n" : 
                    textMessage.getText() + "\n";

                SocketHints socketHints = new SocketHints();
                socketHints.connectTimeout = 4000;

                Socket socket = Gdx.net.newClientSocket(Protocol.TCP, textIPAddress.getText(), PORT, socketHints);
                try {
                    socket.getOutputStream().write(textToSend.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose(); // Ensure the stage is also disposed
    }

    @Override
    public void render() {        
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        stage.draw();
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height); // Update viewport on resize
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
