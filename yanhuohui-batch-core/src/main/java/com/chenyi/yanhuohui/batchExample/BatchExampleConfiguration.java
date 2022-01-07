//package com.chenyi.yanhuohui.batchExample;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.item.file.FlatFileItemWriter;
//import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
//import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.FileSystemResource;
//
//@Configuration
//@EnableBatchProcessing
//public class BatchExampleConfiguration {
//    @Bean
//    public Job helloWorlJob(JobBuilderFactory jobBuilders,
//                            StepBuilderFactory stepBuilders) {
//        return jobBuilders.get("helloWorldJob")
//                .start(helloWorldStep(stepBuilders))
//                .build();
//    }
//    //新建 Step，使用 StepBuilderFactory 创建
//    @Bean
//    public Step helloWorldStep(StepBuilderFactory stepBuilders) {
//        return stepBuilders.get("helloWorldStep")
//                .<Person, String>chunk(10).reader(reader())
//                .processor((Function<? super Person, ? extends String>) processor()).writer(writer()).build();
//    }
//    //读取数据，指定需要读取的资源
//    @Bean
//    public FlatFileItemReader<Person> reader() {
//        return new FlatFileItemReaderBuilder<Person>()
//                .name("personItemReader")
//                .resource(new ClassPathResource("csv/persons.csv"))
//                .delimited().names(new String[] {"firstName", "lastName"})
//                .targetType(Person.class).build();
//    }
//    //处理数据
//    @Bean
//    public PersonItemProcessor processor() {
//        return new PersonItemProcessor();
//    }
//    //写入数据，指定写入路径文件
//    @Bean
//    public FlatFileItemWriter<String> writer() {
//        return new FlatFileItemWriterBuilder<String>()
//                .name("greetingItemWriter")
//                .resource(new FileSystemResource(
//                        "target/test-outputs/greetings.txt"))
//                .lineAggregator(new PassThroughLineAggregator<>()).build();
//    }
//}
