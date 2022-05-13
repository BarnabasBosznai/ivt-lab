package hu.bme.mit.spaceship;

//import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;

  private TorpedoStore mockPrimaryTorpedoStore;
  private TorpedoStore mockSecondaryTorpedoStore;

  @BeforeEach
  public void init(){
    this.mockPrimaryTorpedoStore = mock(TorpedoStore.class);
    this.mockSecondaryTorpedoStore = mock(TorpedoStore.class);

    this.ship = new GT4500(mockPrimaryTorpedoStore, mockSecondaryTorpedoStore);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockPrimaryTorpedoStore, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockSecondaryTorpedoStore.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockPrimaryTorpedoStore, times(1)).fire(1);
    verify(mockSecondaryTorpedoStore, times(1)).fire(1);
  }

  @Test
  void fireTorpedo_SingleSecondary_Success(){
    // Arrange
    when(mockSecondaryTorpedoStore.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockSecondaryTorpedoStore, times(1)).fire(1);
  }

  @Test
  void fireTorpedo_SingleEmptyStores_Failure(){
    // Arrange
    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(true);
    when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockSecondaryTorpedoStore, times(1)).isEmpty();
  }

  @Test
  void fireTorpedo_AllPrimaryEmpty_Failure(){
    // Arrange
    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockPrimaryTorpedoStore, times(1)).isEmpty();
  }

  @Test
  void fireTorpedo_SingleSecondaryEmpty_Success(){
    // Arrange
    when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(true);
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockPrimaryTorpedoStore, times(2)).fire(1);
  }

  @Test
  void fireTorpedo_SinglePrimaryEmpty_Success(){
    // Arrange
    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(true);
    when(mockSecondaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockSecondaryTorpedoStore.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockSecondaryTorpedoStore, times(2)).fire(1);
  }

}
