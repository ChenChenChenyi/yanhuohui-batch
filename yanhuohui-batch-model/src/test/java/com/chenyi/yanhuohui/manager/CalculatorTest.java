package com.chenyi.yanhuohui.manager;

import com.chenyi.yanhuohui.YanhuohuiBatchTestApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.Model;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = YanhuohuiBatchTestApplication.class)
@Slf4j
public class CalculatorTest {
    @Test
    public void testSumXXBySpy_Call_Private_Method() throws Exception {
        Calculator cal= spy(new Calculator());
        when(cal.callSumXX(anyInt(),anyInt())).thenReturn(5);
        //when(cal,"sumXX",anyInt(),anyInt()).thenReturn(2);
        assertEquals(4, cal.callSumXX(1, 2));
    }


    @Test
    public void testSumXXBySpy_Not_Call_Private_Method() throws Exception {
        Calculator cal= spy(new Calculator());
        doReturn(2).when(cal.callSumXX(anyInt(),anyInt()));
        assertEquals(3, cal.callSumXX(1, 2));
    }

    @Test
    public void testSumXXByMock_Not_Call_Real_Method() throws Exception {
        Calculator cal= mock(Calculator.class);
        when(cal.callSumXX(anyInt(),anyInt())).thenReturn(2);
        assertEquals(0, cal.callSumXX(1, 2));
    }
    @Test
    public void testSumXXByMock_Call_Real_Method() throws Exception {
        Calculator cal= mock(Calculator.class);
        when(cal.callSumXX(anyInt(),anyInt())).thenReturn(2);
        when(cal.callSumXX(anyInt(),anyInt())).thenCallRealMethod();//指明callSumXX调用真实的方法
        assertEquals(2, cal.callSumXX(1, 2));
    }

    /**
     * 使用spring容器
     */
//    @Mock
//    private MyRepository myRepository;
//
//    @InjectMocks
//    @Autowired
//    private MyService myService;
//
//    @Autowired
//    private MyController myController;
//
//    @Before
//    public void setUp() throws Exception {
//        MockitoAnnotations.initMocks(this);
//        Model model = new Model(11L, "AAA");
//        doNothing().when(myRepository).doSomething();
//        when(myRepository.findById(11L)).thenReturn(model);
//    }
/**
 * 不借助容器，也可以手动来赋值。在setup方法中做下修改：
 */
//    @Mock
//    private MyRepository myRepository;
//
//    @InjectMocks
//    private MyService myService;
//
//    @InjectMocks
//    private MyController myController;
//
//    @Before
//    public void setUp() throws Exception {
//        //通过ReflectionTestUtils注入需要的非public字段数据
//        ReflectionTestUtils.setField(myController, "myService", myService);
//        Model model = new Model(11L, "AAA");
//        doNothing().when(myRepository).doSomething();
//        when(myRepository.findById(11L)).thenReturn(model);
//    }
}
