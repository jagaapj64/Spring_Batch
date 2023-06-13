package com.example.batch.first.demo.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.example.batch.first.demo.model.CustomerProduct;
import com.example.batch.first.demo.partition.ColumnRangePartitioner;
import com.example.batch.first.demo.repository.EmployeeRepository;

import lombok.AllArgsConstructor;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class SpringBatchConfig {

	private JobBuilderFactory jobBuilderFactory;

	private StepBuilderFactory stepBuilderFactory;

	private EmployeeRepository employeeRepository;

	private CustomerWriter customerWriter;

//	@Bean
//	public FlatFileItemReader<Employee> reader() {
//		FlatFileItemReader<Employee> itemReader = new FlatFileItemReader<>();
//		itemReader.setResource(new FileSystemResource("src/main/resources/employees.csv"));
//		itemReader.setName("csvReader");
//		itemReader.setLinesToSkip(1);
//		itemReader.setLineMapper(lineMapper());
//		return itemReader;
//	}
//
//	private LineMapper<Employee> lineMapper() {
//		DefaultLineMapper<Employee> lineMapper = new DefaultLineMapper<>();
//
//		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
//		lineTokenizer.setDelimiter(",");
//		lineTokenizer.setStrict(false);
//		lineTokenizer.setNames("empId", "firstName", "lastName", "email", "phoneNumber", "hireDate", "jobId", "salary",
//				"managerId ", "departmentId");
//
//		BeanWrapperFieldSetMapper<Employee> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
//		fieldSetMapper.setTargetType(Employee.class);
//
//		lineMapper.setLineTokenizer(lineTokenizer);
//		lineMapper.setFieldSetMapper(fieldSetMapper);
//		return lineMapper;
//
//	}
//
//	@Bean
//	public EmployeeProcessor processor() {
//		return new EmployeeProcessor();
//	}
//
//	@Bean
//	public RepositoryItemWriter<Employee> writer() {
//		RepositoryItemWriter<Employee> writer = new RepositoryItemWriter<>();
//		writer.setRepository(employeeRepository);
//		writer.setMethodName("save");
//		return writer;
//	}
//
//	@Bean
//	public Step step1() {
//		return stepBuilderFactory.get("csv-step").<Employee, Employee>chunk(10).reader(reader()).processor(processor())
//				.writer(writer()).taskExecutor(taskExecutor()).build();
//	}
//
//	@Bean
//	public Job runJob() {
//		return jobBuilderFactory.get("importEmployees").flow(step1()).end().build();
//
//	}
//
//	@Bean
//	public TaskExecutor taskExecutor() {
//		SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
//		asyncTaskExecutor.setConcurrencyLimit(10);
//		return asyncTaskExecutor;
//	}

	@Bean
	FlatFileItemReader<CustomerProduct> reader() {
		FlatFileItemReader<CustomerProduct> itemReader = new FlatFileItemReader<>();
		itemReader.setResource(new FileSystemResource("src/main/resources/organizations-1000.csv"));
		itemReader.setName("csvReader");
		itemReader.setLinesToSkip(1);
		itemReader.setLineMapper(lineMapper());
		return itemReader;
	}

	private LineMapper<CustomerProduct> lineMapper() {
		DefaultLineMapper<CustomerProduct> lineMapper = new DefaultLineMapper<>();

		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames("index", "organizationId", "name","website","country","description","founded","industry","numberOfEmployees");

		BeanWrapperFieldSetMapper<CustomerProduct> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(CustomerProduct.class);

		lineMapper.setLineTokenizer(lineTokenizer);
		lineMapper.setFieldSetMapper(fieldSetMapper);
		return lineMapper;

	}

	@Bean
	CustomerProductProcessor processor() {
		return new CustomerProductProcessor();
	}

	@Bean
	ColumnRangePartitioner partitioner() {
		return new ColumnRangePartitioner();
	}

	@Bean
	PartitionHandler partitionHandler() {
		TaskExecutorPartitionHandler taskExecutorPartitionHandler = new TaskExecutorPartitionHandler();
		taskExecutorPartitionHandler.setGridSize(5);
		taskExecutorPartitionHandler.setTaskExecutor(taskExecutor());
		taskExecutorPartitionHandler.setStep(slaveStep());
		return taskExecutorPartitionHandler;
	}

	@Bean
	Step slaveStep() {
		return stepBuilderFactory.get("slaveStep").<CustomerProduct, CustomerProduct>chunk(200).reader(reader())
				.processor(processor()).writer(customerWriter).build();
	}

	@Bean
	Step masterStep() {
		return stepBuilderFactory.get("masterStep").partitioner(slaveStep().getName(), partitioner())
				.partitionHandler(partitionHandler()).build();
	}

	@Bean
	Job runJob() {
		return jobBuilderFactory.get("importEmployees").flow(masterStep()).end().build();

	}

	@Bean
	@Async
	TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setMaxPoolSize(20);
		taskExecutor.setCorePoolSize(20);
		taskExecutor.setQueueCapacity(20);
		return taskExecutor;
	}

}
